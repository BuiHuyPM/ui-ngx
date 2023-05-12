import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NoLicenseRoutingModule } from './no-license-routing.module';
import { InsertLicenseComponent } from './pages/insert-license/insert-license.component';
import {TranslateModule} from '@ngx-translate/core';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import {FlexModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {SharedModule} from '@shared/shared.module';


@NgModule({
  declarations: [
    InsertLicenseComponent
  ],
    imports: [
        CommonModule,
        NoLicenseRoutingModule,
        TranslateModule,
        MatButtonModule,
        MatIconModule,
        MatTooltipModule,
        FlexModule,
        FormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatProgressBarModule,
        ReactiveFormsModule,
        SharedModule
    ]
})
export class NoLicenseModule { }
