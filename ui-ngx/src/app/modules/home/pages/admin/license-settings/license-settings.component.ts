import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AdminSettings, GeneralSettings, LicenseSettings} from '@shared/models/settings.models';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {Router} from '@angular/router';
import {AdminService} from '@core/http/admin.service';
import {PageComponent} from '@shared/components/page.component';
import {HasConfirmForm} from '@core/guards/confirm-on-exit.guard';
import {LicenseService} from '@core/http/license.service';

@Component({
  selector: 'tb-license-settings',
  templateUrl: './license-settings.component.html',
  styleUrls: ['./license-settings.component.scss']
})
export class LicenseSettingsComponent extends PageComponent implements OnInit, HasConfirmForm {

  generalSettings: FormGroup;
  adminSettings: AdminSettings<LicenseSettings>;
  constructor(
    protected store: Store<AppState>,
    private router: Router,
    private licenseService: LicenseService,
    public fb: FormBuilder) {
    super(store);
  }

  ngOnInit(): void {
    this.buildGeneralServerSettingsForm();
    this.licenseService.getLicense().subscribe(
      (adminSettings) => {
        this.adminSettings = adminSettings;
        this.generalSettings.reset(this.adminSettings.jsonValue);
      },
      () => {
        this.adminSettings = {
          key: 'license-key',
          jsonValue: {
            licenseKey: '',
            isHardKey: '',
            expirationTime: null
          }
        };
        this.generalSettings.reset(this.adminSettings.jsonValue);
      }
    );
  }
  confirmForm(): FormGroup {
    return this.generalSettings;
  }
  save() {
    this.adminSettings.jsonValue = {...this.adminSettings.jsonValue, ...this.generalSettings.value};
    this.licenseService.saveLicense(this.adminSettings).subscribe(
      async (adminSettings) => {
        this.adminSettings = adminSettings;
        this.generalSettings.reset(this.adminSettings.jsonValue);
      }
    );
  }
  private buildGeneralServerSettingsForm() {
    this.generalSettings = this.fb.group({
      licenseKey: ['', [Validators.required]],
      isHardKey: ['', []],
      expirationTime: [null, []]
    });
  }
}
