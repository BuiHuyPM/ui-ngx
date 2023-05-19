import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AdminSettings, LicenseSettings} from '@shared/models/settings.models';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {Router} from '@angular/router';
import {LicenseService} from '@core/http/license.service';
import {PageComponent} from '@shared/components/page.component';
import {HasConfirmForm} from '@core/guards/confirm-on-exit.guard';
import {AssetFilesService} from '@core/http/asset-files.service';

@Component({
  selector: 'tb-asset-files',
  templateUrl: './asset-files.component.html',
  styleUrls: ['./asset-files.component.scss']
})
export class AssetFilesComponent extends PageComponent implements OnInit, HasConfirmForm {
  generalSettings: FormGroup;
  adminSettings: AdminSettings<LicenseSettings>;
  constructor(
    protected store: Store<AppState>,
    private router: Router,
    private assetFilesService: AssetFilesService,
    public fb: FormBuilder) {
    super(store);
  }

  ngOnInit(): void {
    this.buildGeneralServerSettingsForm();
    this.assetFilesService.get('').subscribe(
      (assetFiles) => {
        console.log(assetFiles);
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

}
