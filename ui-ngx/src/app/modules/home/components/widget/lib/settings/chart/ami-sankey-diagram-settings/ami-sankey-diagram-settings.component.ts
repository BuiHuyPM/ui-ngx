import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';
import {WidgetSettings, WidgetSettingsComponent} from '@shared/models/widget.models';

@Component({
  selector: 'tb-ami-sankey-diagram-settings',
  templateUrl: './ami-sankey-diagram-settings.component.html',
  styleUrls: ['./../../widget-settings.scss']
})
export class AmiSankeyDiagramSettingsComponent extends WidgetSettingsComponent {

  widgetSettingsForm: FormGroup;

  constructor(protected store: Store<AppState>, private fb: FormBuilder) {
    super(store);
  }
  protected defaultSettings(): WidgetSettings {
    return {
      general: {
        tool: true,
        showLastValue: false
      },
      tooltip: {
        headerFormat: '',
        pointFormat: '',
        nodeFormat: '',
      },
      colors: []
    };
  }

  protected onSettingsSet(settings: WidgetSettings) {
    this.widgetSettingsForm = this.fb.group({
      general: this.fb.group({
        tool: [true],
        showLastValue: [false],
      }),
      tooltip: this.fb.group({
        headerFormat: [''],
        pointFormat: [''],
        nodeFormat: [''],
      }),
      colors: this.fb.array([])
    });

    this.widgetSettingsForm.get('general.tool').setValue(settings?.general?.tool === true);
    this.widgetSettingsForm.get('general.showLastValue').setValue(settings?.general?.showLastValue === true);

    this.widgetSettingsForm.get('tooltip.headerFormat').setValue(settings?.tooltip?.headerFormat || null);
    this.widgetSettingsForm.get('tooltip.pointFormat').setValue(settings?.tooltip?.pointFormat || null);
    this.widgetSettingsForm.get('tooltip.nodeFormat').setValue(settings?.tooltip?.nodeFormat || null);

    for (const color of settings?.colors || []) {
      this.colors.push(this.fb.control(color));
    }
  }
  protected settingsForm(): FormGroup {
    return this.widgetSettingsForm;
  }
  addColors() {
    this.colors.push(this.fb.control(''));
  }

  removeColors(i: number) {
    this.colors.removeAt(i);
  }

  get colors() {
    return this.widgetSettingsForm.get('colors') as FormArray;
  }


}
