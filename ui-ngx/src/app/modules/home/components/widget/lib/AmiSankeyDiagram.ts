import {WidgetContext} from '@home/models/widget-component.models';
import * as Highcharts from 'highcharts';
import sankey from 'highcharts/modules/sankey';
import seriesLabel from 'highcharts/modules/series-label';
import exporting from 'highcharts/modules/exporting';
import exportData from 'highcharts/modules/export-data';
import accessibility from 'highcharts/modules/accessibility';

sankey(Highcharts);
seriesLabel(Highcharts);
exporting(Highcharts);
exportData(Highcharts);
accessibility(Highcharts);
const TIME_UPDATE = 2000;

export class AmiSankeyDiagram {
  private readonly ctx: WidgetContext;
  private chart: Highcharts.Chart;
  private lastUpdateAt: number;
  private updateTimes: number;
  constructor(ctx: WidgetContext) {
    this.ctx = ctx;
    this.init(this.ctx);
  }
  private init(ctx: WidgetContext) {
    this.updateTimes = -1;
    this.lastUpdateAt = new Date().getTime() - TIME_UPDATE;
    const settings = ctx.settings;
    // @ts-ignore
    const id = crypto.randomUUID();
    const chartOptions = {
      exporting: {
        enabled: settings?.general?.tool || false
      },
      title: {
        text: ''
      },
      tooltip: {
        headerFormat: settings?.tooltip?.headerFormat || null,
        pointFormat: settings?.tooltip?.pointFormat || null,
        nodeFormat: settings?.tooltip?.nodeFormat || null
      },
      credits: {
        enabled: false
      },
      colors: settings?.colors && settings?.colors.length > 0 ? settings?.colors :  Highcharts.getOptions().colors,
      series: [{
        name: 'sankey',
        keys: ['from', 'to', 'weight'],
        data: [],
        type: 'sankey'
      }]
    };
    ctx.$container.append(`<div id="${id}" style="display: block;width: 100%;height: 100%;"></div>`);
    // @ts-ignore
    this.chart = Highcharts.chart(id, chartOptions);
  }

  public series(ctx: WidgetContext): any {
    const data = ctx.data;
    const latestData = ctx.latestData;

    // quy chuẩn hóa cây nhóm và thiết bị
    const groups: Array<{ name: string, items: Array<{ name: string, devices: Array<{ name: string, value: number }> }> }> = [];

    const devices: Array<{ name: string, value: number }> = [];
    for (const datum of data) {
      devices.push({
        name: datum.datasource.entityName,
        value: datum.data.reduce((previousValue, currentValue) => {
          return previousValue + (currentValue?.[1] || 0);
        }, 0)
      });
    }

    for (const latestDatum of latestData) {
      const groupIndex = groups.findIndex(value => value.name === latestDatum.dataKey.name);

      const device = devices.find(d => d.name === latestDatum.datasource.entityName);

      if (!device) { return; }

      if (groupIndex > -1) {
        const itemIndex = groups[groupIndex].items.findIndex(value => value.name === latestDatum.data[0][1]);
        if (itemIndex > -1) {
          groups[groupIndex].items[itemIndex].devices.push(device);
        } else {
          groups[groupIndex].items.push({
            name: latestDatum.data[0][1],
            devices: [device]
          });
        }
      } else {
        groups.push({
          name: latestDatum.dataKey.name,
          items: [{
            name: latestDatum.data[0][1],
            devices: [device]
          }]
        });
      }
    }
    // quy chuẩn hóa cây nhóm và thiết bị
    // đấy giá trị vào series để vẽ biểu đồ
    const seriesData: any[][] = [];
    groups.forEach((value, index, array) => {
      const nextGroup = groups[index + 1];
      if (nextGroup) {
        value.items.forEach((valueItem) => {
          const nextItems = nextGroup.items.filter(item => {
            return item.devices.some(device => {
              return valueItem.devices.some(device1 => device1.name === device.name);
            });
          });
          for (const nextItem of nextItems) {
            const val = nextItem.devices.filter(device => {
              return valueItem.devices.some(device1 => device1.name === device.name);
            }).reduce((pre, cur) => pre + cur.value, 0);
            seriesData.push([valueItem.name, nextItem.name, val]);
          }
        });
      }else if ( ctx.settings?.general?.showLastValue === true){
        value.items.forEach((valueItem) => {
          for (const device of valueItem.devices) {
            seriesData.push([valueItem.name, device.name, device.value]);
          }
        });
      }
    });
    // đấy giá trị vào series để vẽ biểu đồ
    return {
      data: seriesData,
    };
  }

  public update() {
    // @ts-ignore
    if (this.lastUpdateAt + TIME_UPDATE > new Date().getTime() && (this.ctx?.widget?.config?.useDashboardTimewindow || this.ctx?.widget?.config?.timewindow?.realtime) && this.updateTimes > 1) {
      return;
    }
    const newSeries = this.series(this.ctx);
    if (newSeries.length > 0 && newSeries[0].data.length > 0) {
      this.lastUpdateAt = new Date().getTime();
      this.updateTimes += 1;
    }
    const oldSeries = this.chart.series[0];
    if (oldSeries) {
      oldSeries.update(newSeries);
    }else{
      this.chart.addSeries(newSeries);
    }
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
