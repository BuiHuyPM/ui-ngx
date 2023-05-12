import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EntitiesVersionControlService} from '@core/http/entities-version-control.service';
import {defaultHttpOptionsFromConfig, RequestConfig} from '@core/http/http-utils';
import {Observable} from 'rxjs';
import {AdminSettings, LicenseSettings} from '@shared/models/settings.models';

@Injectable({
  providedIn: 'root'
})
export class LicenseService {
  constructor(
    private http: HttpClient
  ) {
  }

  public getLicense(config?: RequestConfig): Observable<AdminSettings<LicenseSettings>> {
    return this.http.get<AdminSettings<LicenseSettings>>('api/license', defaultHttpOptionsFromConfig(config));
  }

  public saveLicense(adminSettings: AdminSettings<LicenseSettings>, config?: RequestConfig): Observable<AdminSettings<LicenseSettings>> {
    return this.http.post<AdminSettings<LicenseSettings>>('api/license', adminSettings, defaultHttpOptionsFromConfig(config));
  }
}
