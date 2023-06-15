import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {AssetFile} from '@shared/models/settings.models';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {PageComponent} from '@shared/components/page.component';
import {AssetFilesService} from '@core/http/asset-files.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatCheckboxChange} from '@angular/material/checkbox';
import {DialogService} from '@core/services/dialog.service';
import {TranslateService} from '@ngx-translate/core';
import {forkJoin} from 'rxjs';
import {FormCreateComponent} from '@home/pages/admin/asset-files/form-create/form-create.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'tb-asset-files',
  templateUrl: './asset-files.component.html',
  styleUrls: ['./asset-files.component.scss', '../settings-card.scss']
})
export class AssetFilesComponent extends PageComponent implements OnInit {
  assetFiles: AssetFile[] = [];
  dataSource: MatTableDataSource<AssetFile>;
  displayedColumns: string[] = ['checkbox', 'name', 'path', 'lastModified', 'action'];
  public checkList: Set<string> = new Set<string>([]);

  constructor(
    protected store: Store<AppState>,
    private router: Router,
    private dialog: MatDialog,
    public translate: TranslateService,
    private dialogService: DialogService,
    private assetFilesService: AssetFilesService,
    public fb: FormBuilder,
    private route: ActivatedRoute) {
    super(store);
    router.events.subscribe((ev) => {
      if (ev instanceof NavigationEnd) {
        this.fetchFolder();
      }
    });
  }

  ngOnInit(): void {
    this.fetchFolder();
  }

  public fetchFolder(): void {
    const routeParams = this.route.snapshot.paramMap;
    const folder = routeParams.get('folder') || '';
    const isRoot = !folder;
    this.checkList.clear();
    this.dataSource = new MatTableDataSource<AssetFile>([]);
    this.assetFilesService.get(folder).subscribe(
      (assetFiles) => {
        const af = [...assetFiles];
        if (!isRoot) {
          const paths: string[] = folder.split('/');
          paths.pop();
          af.unshift({
            name: '..',
            path: paths.join('/'),
            isFolder: true,
            data: null,
            lastModified: null
          });
        }
        this.assetFiles = assetFiles;
        this.dataSource = new MatTableDataSource<AssetFile>(af);
      }
    );
  }

  onClickFolder($event: MouseEvent, row: AssetFile) {
    if (row.isFolder) {
      this.router.navigateByUrl('/settings/asset-files' + row.path);
    } else {
      window.open('/assetFiles' + row.path);
    }
  }

  checkAll($event: MatCheckboxChange) {
    if ($event.checked) {
      this.assetFiles.forEach(value => {
        this.checkList.add(value.path);
      });
    } else {
      this.checkList.clear();
    }
  }

  checkElement($event: MatCheckboxChange, element: AssetFile) {
    if ($event.checked) {
      this.checkList.add(element.path);
    } else {
      this.checkList.delete(element.path);
    }
  }

  removeElement($event: MouseEvent, element: AssetFile) {
    this.dialogService.confirm(
      this.translate.instant('attribute.delete-attributes-title', {count: 1}),
      this.translate.instant('attribute.delete-attributes-text'),
      this.translate.instant('action.no'),
      this.translate.instant('action.yes'),
      true
    ).subscribe((result) => {
      if (result) {
        this.assetFilesService.delete(element.path).subscribe(() => {
          this.fetchFolder();
        });
      }
    });
  }

  multiRemove() {
    if (this.checkList.size > 0) {
      this.dialogService.confirm(
        this.translate.instant('attribute.delete-attributes-title', {count: this.checkList.size}),
        this.translate.instant('attribute.delete-attributes-text'),
        this.translate.instant('action.no'),
        this.translate.instant('action.yes'),
        true
      ).subscribe(async (result) => {
        if (!result) {
          return false;
        }

        const ob = [];
        for (const path of this.checkList) {
          ob.push(this.assetFilesService.delete(path));
        }
        forkJoin(ob).subscribe(() => {
          this.fetchFolder();
        });
      });
    }
  }


  openAddNew($event: MouseEvent, isFolder: boolean) {
    const routeParams = this.route.snapshot.paramMap;
    const folder = routeParams.get('folder') || '';
    this.dialog.open(FormCreateComponent, {
      data: {folder, isFolder},
      disableClose: true,
      panelClass: ['tb-dialog', 'tb-fullscreen-dialog']
    }).afterClosed().subscribe((value) => {
      if (value) {
        this.fetchFolder();
      }
    });
  }
}
