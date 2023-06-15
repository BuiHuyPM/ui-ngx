import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {EntitiesVersionControlService} from '@core/http/entities-version-control.service';
import {defaultHttpOptionsFromConfig, RequestConfig} from '@core/http/http-utils';
import {Observable} from 'rxjs';
import {AdminSettings, AssetFile, LicenseSettings} from '@shared/models/settings.models';

@Injectable({
  providedIn: 'root'
})
export class AssetFilesService {
  private baseUrl = '/api/assetFiles';

  constructor(
    private http: HttpClient
  ) {
  }

  public get(path: string, config?: RequestConfig): Observable<AssetFile[]> {
    return this.http.get<AssetFile[]>(this.baseUrl + path, defaultHttpOptionsFromConfig(config));
  }
  public create(path: string, files: AssetFile[], config?: RequestConfig): Observable<void> {
    return this.http.post<void>(this.baseUrl + path, files, defaultHttpOptionsFromConfig(config));
  }
  public delete(path: string, config?: RequestConfig): Observable<void> {
    return this.http.delete<void>(this.baseUrl + path, defaultHttpOptionsFromConfig(config));
  }
}
