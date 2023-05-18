import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {PageComponent} from '@shared/components/page.component';
import {HasConfirmForm} from '@core/guards/confirm-on-exit.guard';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {AdminSettings, LicenseSettings} from '@shared/models/settings.models';
import {LicenseService} from '@core/http/license.service';

@Component({
  selector: 'tb-insert-license',
  templateUrl: './insert-license.component.html',
  styleUrls: ['./insert-license.component.scss']
})
export class InsertLicenseComponent extends PageComponent implements OnInit, HasConfirmForm{
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
            isHardKey: ''
          }
        };
        this.generalSettings.reset(this.adminSettings.jsonValue);
      }
    );
  }

  async reload() {
    await this.router.navigate(['/']);
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
        await this.reload();
      }
    );
  }

  private buildGeneralServerSettingsForm() {
    this.generalSettings = this.fb.group({
      licenseKey: ['', [Validators.required]],
      isHardKey: ['', []]
    });
  }
}
