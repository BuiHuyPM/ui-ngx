import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {InsertLicenseComponent} from '@modules/no-license/pages/insert-license/insert-license.component';
import {AuthGuard} from '@core/guards/auth.guard';

const routes: Routes = [
  {
    path: 'insert-license',
    component: InsertLicenseComponent,
    data: {
      title: 'license.insert',
      module: 'public'
    },
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NoLicenseRoutingModule { }
