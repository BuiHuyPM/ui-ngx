import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Store} from '@ngrx/store';
import {TranslateService} from '@ngx-translate/core';
import {AppState} from '@core/core.state';
import {ActionNotificationShow} from '@core/notification/notification.actions';
import {AssetFilesService} from '@core/http/asset-files.service';
import {AssetFile} from '@shared/models/settings.models';

@Component({
  selector: 'tb-form-create',
  templateUrl: './form-create.component.html',
  styleUrls: ['./form-create.component.scss']
})
export class FormCreateComponent implements OnInit {
  isLoading$ = false;
  assetFileFormGroup = this.fb.group({
    fileAttach: [''],
    fileName: ['', Validators.required]
  });


  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {
      folder: string;
      isFolder: boolean;
    },
    @Inject(Store) private store: Store<AppState>,
    private dialogRef: MatDialogRef<FormCreateComponent>,
    private fb: FormBuilder,
    public translate: TranslateService,
    private assetFilesService: AssetFilesService
  ) {
  }

  ngOnInit(): void {
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  convertToBase64File(data: string): string {
    return window.btoa(data);
  }

  onSubmit(): void {
    const formData = this.assetFileFormGroup.value;
    const assetFiles: AssetFile[] = [];
    if (Array.isArray(formData.fileName)) {
      formData.fileName.forEach((name: string, index) => {
        assetFiles.push({
          name: name || '',
          path: '',
          data: formData.fileAttach?.[index] || null,
          isFolder: this.data.isFolder
        });
      });
    } else {
      assetFiles.push({
        name: formData.fileName,
        path: '',
        data: formData.fileAttach,
        isFolder: this.data.isFolder
      });
    }

    this.assetFilesService.create(this.data.folder, assetFiles).subscribe(() => {
      this.store.dispatch(new ActionNotificationShow(
        {
          message: this.translate.instant(this.data.isFolder ? 'asset-file.create-folder-successful' : 'asset-file.upload-file-successful'),
          type: 'success',
          duration: 750,
        }));
      this.dialogRef.close(true);
    });
  }

}
