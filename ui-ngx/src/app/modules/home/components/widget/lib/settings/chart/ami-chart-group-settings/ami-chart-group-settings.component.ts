import {Component} from '@angular/core';
import {WidgetSettings, WidgetSettingsComponent} from '@shared/models/widget.models';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {Store} from '@ngrx/store';
import {AppState} from '@core/core.state';

@Component({
  selector: 'tb-ami-chart-group-settings',
  templateUrl: './ami-chart-group-settings.component.html',
  styleUrls: ['./../../widget-settings.scss']
})
export class AmiChartGroupSettingsComponent extends WidgetSettingsComponent {

  widgetSettingsForm: FormGroup;

  constructor(protected store: Store<AppState>, private fb: FormBuilder) {
    super(store);
  }

  protected onSettingsSet(settings: WidgetSettings) {
    this.widgetSettingsForm = this.fb.group({
      general: this.fb.group({
        tool: [true]
      }),
      legend: this.fb.group({
        enabled: [true],
        align: ['center'],
        verticalAlign: ['bottom'],
        layout: ['horizontal'],
      }),
      xAxis: this.fb.group({
        title: this.fb.group({
          text: ['']
        }),
        type: ['datetime'],
        labels: this.fb.group({
          format: ['']
        }),
        reversed: [false],
        opposite: [false],
      }),
      yAxis: this.fb.group({
        title: this.fb.group({
          text: ['']
        }),
        type: ['linear'],
        labels: this.fb.group({
          format: ['']
        }),
        reversed: [false],
        opposite: [false],
      }),
      tooltip: this.fb.group({
        format: [''],
        headerFormat: [''],
        pointFormat: [''],
        footerFormat: [''],
      }),
      colors: this.fb.array([]),
      series: this.fb.group({
          innerSize: [''],
          dataLabels: this.fb.group({
            enabled: [false],
            format: ['']
          })
        }),
    });

    this.widgetSettingsForm.get('general.tool').setValue(settings?.general?.tool || true);

    this.widgetSettingsForm.get('legend.enabled').setValue(settings?.legend?.enabled || true);
    this.widgetSettingsForm.get('legend.align').setValue(settings?.legend?.align || 'center');
    this.widgetSettingsForm.get('legend.verticalAlign').setValue(settings?.legend?.verticalAlign || 'bottom');
    this.widgetSettingsForm.get('legend.layout').setValue(settings?.legend?.layout || 'horizontal');

    this.widgetSettingsForm.get('xAxis.title.text').setValue(settings?.xAxis?.title?.text || '');
    this.widgetSettingsForm.get('xAxis.type').setValue(settings?.xAxis?.type || 'datetime');
    this.widgetSettingsForm.get('xAxis.labels.format').setValue(settings?.xAxis?.labels?.format || '');
    this.widgetSettingsForm.get('xAxis.reversed').setValue(settings?.xAxis?.reversed || false);
    this.widgetSettingsForm.get('xAxis.opposite').setValue(settings?.xAxis?.opposite || false);

    this.widgetSettingsForm.get('yAxis.title.text').setValue(settings?.yAxis?.title?.text || '');
    this.widgetSettingsForm.get('yAxis.type').setValue(settings?.yAxis?.type || 'linear');
    this.widgetSettingsForm.get('yAxis.labels.format').setValue(settings?.yAxis?.labels?.format || '');
    this.widgetSettingsForm.get('yAxis.reversed').setValue(settings?.yAxis?.reversed || false);
    this.widgetSettingsForm.get('yAxis.opposite').setValue(settings?.yAxis?.opposite || false);

    this.widgetSettingsForm.get('tooltip.format').setValue(settings?.tooltip?.format || '');
    this.widgetSettingsForm.get('tooltip.headerFormat').setValue(settings?.tooltip?.headerFormat || '');
    this.widgetSettingsForm.get('tooltip.pointFormat').setValue(settings?.tooltip?.pointFormat || '');
    this.widgetSettingsForm.get('tooltip.footerFormat').setValue(settings?.tooltip?.footerFormat || '');

    for (const color of settings?.colors || []) {
      this.colors.push(this.fb.control(color));
    }

    this.widgetSettingsForm.get('series.innerSize').setValue(settings?.plotOptions.series.innerSize || null);
    this.widgetSettingsForm.get('series.dataLabels.enabled').setValue(settings?.plotOptions.series.dataLabels.enabled || true);
    this.widgetSettingsForm.get('series.dataLabels.format').setValue(settings?.plotOptions.series.dataLabels.format || '');
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

  protected settingsForm(): FormGroup {
    return this.widgetSettingsForm;
  }

  protected defaultSettings(): WidgetSettings {
    return {
      general: {
        tool: true
      },
      legend: {
        enabled: true,
        align: 'center',
        verticalAlign: 'bottom',
        layout: 'vertical'
      },
      xAxis: {
        title: {
          text: ''
        },
        type: 'datetime',
        labels: {
          format: ''
        },
        reversed: false,
        opposite: false,
      },
      yAxis: {
        title: {
          text: ''
        },
        type: 'datetime',
        labels: {
          format: ''
        },
        reversed: false,
        opposite: false,
      },
      tooltip: {
        format: '',
        headerFormat: '',
        footerFormat: '',
        pointFormat: '',
      },
      colors: [],
      plotOptions: {
        series: {
          dataLabels: {
            enabled: false,
            format: ''
          }
        },
      },
    };
  }
}
