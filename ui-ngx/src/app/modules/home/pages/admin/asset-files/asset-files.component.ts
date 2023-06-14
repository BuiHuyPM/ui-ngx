import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AdminSettings, AssetFile, LicenseSettings} from '@shared/models/settings.models';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {PageComponent} from '@shared/components/page.component';
import {HasConfirmForm} from '@core/guards/confirm-on-exit.guard';
import {AssetFilesService} from '@core/http/asset-files.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatCheckboxChange} from '@angular/material/checkbox';
import {AttributeScope} from '@shared/models/telemetry/telemetry.models';
import {DialogService} from '@core/services/dialog.service';
import {TranslateService} from '@ngx-translate/core';
import {forkJoin, Observable} from 'rxjs';

@Component({
  selector: 'tb-asset-files',
  templateUrl: './asset-files.component.html',
  styleUrls: ['./asset-files.component.scss', '../settings-card.scss']
})
export class AssetFilesComponent extends PageComponent implements OnInit, HasConfirmForm {
  generalSettings: FormGroup;
  adminSettings: AdminSettings<LicenseSettings>;
  assetFiles: AssetFile[] = [];
  dataSource: MatTableDataSource<AssetFile>;
  displayedColumns: string[] = ['checkbox', 'name', 'path', 'lastModified', 'action'];
  public checkList: Set<string> = new Set<string>([]);

  constructor(
    protected store: Store<AppState>,
    private router: Router,
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
    this.buildGeneralServerSettingsForm();
    this.fetchFolder();
  }

  public fetchFolder(): void {
    const routeParams = this.route.snapshot.paramMap;
    const folder = routeParams.get('folder') || '';
    const isRoot = !folder;
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

  confirmForm(): FormGroup {
    return this.generalSettings;
  }

  save() {
    // this.adminSettings.jsonValue = {...this.adminSettings.jsonValue, ...this.generalSettings.value};
    // this.assetFilesService.saveLicense(this.adminSettings).subscribe(
    //   async (adminSettings) => {
    //     this.adminSettings = adminSettings;
    //     this.generalSettings.reset(this.adminSettings.jsonValue);
    //   }
    // );
  }

  private buildGeneralServerSettingsForm() {
    // this.generalSettings = this.fb.group({
    //   licenseKey: ['', [Validators.required]],
    //   isHardKey: ['', []],
    //   expirationTime: [null, []]
    // });
  }


  onClickFolder($event: MouseEvent, row: AssetFile) {
    if (row.isFolder) {
      this.router.navigateByUrl('/settings/asset-files' + row.path);
    } else {
      window.open('/assetFiles' + row.path);
    }
  }

  checkAll($event: MatCheckboxChange) {
    console.log($event);
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
    if (this.checkList.size > 0){
      this.dialogService.confirm(
        this.translate.instant('attribute.delete-attributes-title', {count: this.checkList.size}),
        this.translate.instant('attribute.delete-attributes-text'),
        this.translate.instant('action.no'),
        this.translate.instant('action.yes'),
        true
      ).subscribe(async (result) => {
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
}
