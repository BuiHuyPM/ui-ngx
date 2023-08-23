import {WidgetContext} from '@home/models/widget-component.models';
import * as Highcharts from 'highcharts';
import seriesLabel from 'highcharts/modules/series-label';
import exporting from 'highcharts/modules/exporting';
import exportData from 'highcharts/modules/export-data';
import accessibility from 'highcharts/modules/accessibility';

seriesLabel(Highcharts);
exporting(Highcharts);
exportData(Highcharts);
accessibility(Highcharts);

type ChartType = 'line' | 'spline' | 'pie' | 'bar' | 'column';
const TIME_UPDATE = 2000;

function nullOrEmpty(value: string | string[] | null | undefined, defaultValue: string | string[]): string | string[] {
  if (!value || value.length === 0) {
    return defaultValue;
  }
  return value;
}

export class AmiChartGroup {
  private readonly ctx: WidgetContext;
  private readonly chartType: ChartType;
  private chart: Highcharts.Chart;
  private lastUpdateAt: number;

  constructor(ctx: WidgetContext, chartType: ChartType | null) {
    this.lastUpdateAt = new Date().getTime() - TIME_UPDATE;
    this.ctx = ctx;
    if (chartType == null) {
      this.chartType = 'pie';
    } else {
      this.chartType = chartType;
    }
    this.init(this.ctx, this.chartType);
  }

  public init(ctx: WidgetContext, chartType: ChartType) {
    const settings = ctx.settings;
    // @ts-ignore
    const id = crypto.randomUUID();
    const chartOptions = {
      chart: {
        type: chartType
      },
      title: {
        text: '',
      },
      legend: {
        enabled: settings?.legend?.enabled || false,
        align: settings?.legend?.align || 'center',
        verticalAlign: settings?.legend?.verticalAlign || 'bottom',
        layout: settings?.legend?.layout || 'horizontal',
      },
      tooltip: {
        format: nullOrEmpty(settings?.tooltip?.format, Highcharts.getOptions().tooltip.format),
        headerFormat: nullOrEmpty(settings?.tooltip?.headerFormat, Highcharts.getOptions().tooltip.headerFormat),
        pointFormat: nullOrEmpty(settings?.tooltip?.pointFormat, Highcharts.getOptions().tooltip.pointFormat),
        footerFormat: nullOrEmpty(settings?.tooltip?.footerFormat, Highcharts.getOptions().tooltip.footerFormat),
        shared: true
      },
      colors: settings?.colors && settings?.colors.length > 0 ? settings?.colors :  Highcharts.getOptions().colors,
      credits: {
        enabled: false
      },
      xAxis: {
        title: {
          text: settings?.xAxis?.title?.text || ''
        },
        type: settings?.xAxis?.type || 'datetime',
        labels: {
          format: settings?.xAxis?.labels?.format || null
        },
        reversed: settings?.xAxis?.reversed || false,
        opposite: settings?.xAxis?.opposite || false,
      },
      yAxis: {
        type: settings?.yAxis?.type || 'linear',
        title: {
          text: settings?.yAxis?.title?.text || ''
        },
        labels: {
          format: settings?.yAxis?.labels?.format || null
        },
        reversed: settings?.yAxis?.reversed || false,
        opposite: settings?.yAxis?.opposite || false,
      },
      plotOptions: {
        series: {
          innerSize: settings?.series?.innerSize,
          dataLabels: {
            enabled: settings?.series?.dataLabels?.enabled || false,
            format: settings?.series?.dataLabels?.format || '{y}'
          }
        },
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          showInLegend: true
        }
      },
      series: []
    };
    ctx.$container.append(`<div id="${id}" style="display: block;width: 100%;height: 100%;"></div>`);
    // @ts-ignore
    this.chart = Highcharts.chart(id, chartOptions);
  }

  public series(ctx: WidgetContext, chartType: ChartType): any {
    switch (chartType) {
      case 'line':
      case 'spline':
      case 'column':
      case 'bar':
        return this.seriesLine(ctx);
      case 'pie':
        return this.seriesPie(ctx);
      default:
        return this.seriesPie(ctx);
    }
  }

  public seriesLine(ctx: WidgetContext): any {
    const data = ctx.data;
    const timeWindow = ctx.timeWindow;
    const latestData = ctx.latestData;
    const seriesData: Array<{ name: string, data: number[][] }> = [];
    data.forEach((value, index, array) => {
      value.data.forEach(value1 => {
        const aggTimestamp = Math.floor(timeWindow.minTime +
          Math.floor((value1[0] - timeWindow.minTime) / timeWindow.interval) * timeWindow.interval +
          timeWindow.interval / 2);
        const groupName = (latestData?.[index]?.data?.[0]?.[1] || '') + '';
        const preIndex = seriesData.findIndex(value2 => {
          return value2.name === groupName;
        });
        if (preIndex > -1) {
          const dataIndex = seriesData[preIndex].data.findIndex(value2 => value2[0] === aggTimestamp);
          if (dataIndex > -1) {
            seriesData[preIndex].data[dataIndex][1] += value1[1];
          } else {
            seriesData[preIndex].data.push([aggTimestamp, value1[1]]);
          }
        } else {
          seriesData.push({
            name: groupName,
            data: [[aggTimestamp, value1[1]]],
          });
        }
      });
    });

    return seriesData;
  }

  public seriesPie(ctx: WidgetContext): any {
    const data = ctx.data;
    const latestData = ctx.latestData;
    const seriesData: Array<{ name: string, y: number }> = [];
    data.forEach((value, index) => {
      const groupName = (latestData?.[index]?.data?.[0]?.[1] || '') + '';
      const preIndex = seriesData.findIndex(value1 => value1.name === groupName);
      const newData = value.data.reduce((previousValue, currentValue) => {
        return previousValue + (currentValue?.[1] || 0);
      }, 0);
      if (preIndex > -1) {
        seriesData[preIndex].y += newData;
      } else {
        seriesData.push({
          name: groupName,
          y: newData
        });
      }
    });
    return [{
      name: 'Pie',
      data: seriesData
    }];
  }

  public update() {
    // @ts-ignore
    if (this.lastUpdateAt + TIME_UPDATE > new Date().getTime() && (this.ctx?.widget?.config?.useDashboardTimewindow || this.ctx?.widget?.config?.timewindow?.realtime)) {
      return;
    }
    const newSeries = this.series(this.ctx, this.chartType);

    if (newSeries.length > 0 && newSeries[0].data.length > 0) {
      this.lastUpdateAt = new Date().getTime();
    }

    newSeries.forEach((newSery, index) => {
      const oldSeries = this.chart.series[index];
      if (oldSeries) {
        oldSeries.update(newSery);
      } else {
        this.chart.addSeries(newSery);
      }
    });
  }

  public latestDataUpdate() {
  }

  public resize() {
  }

  public checkMouseEvents() {
  }

  public destroy() {
    this.chart.destroy();
  }
}
