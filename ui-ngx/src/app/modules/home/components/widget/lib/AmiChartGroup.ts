import {WidgetContext} from '@home/models/widget-component.models';
import {ChartType} from '@home/components/widget/lib/flot-widget.models';
import Highcharts, {Chart} from 'highcharts';
import seriesLabel from 'highcharts/modules/series-label';
import exporting from 'highcharts/modules/exporting';
import exportData from 'highcharts/modules/export-data';
import accessibility from 'highcharts/modules/accessibility';

seriesLabel(Highcharts);
exporting(Highcharts);
exportData(Highcharts);
accessibility(Highcharts);

export class AmiChartGroup {
    private ctx: WidgetContext;
    private chartType: ChartType;
    private chart: Chart;

    constructor(ctx: WidgetContext, chartType: ChartType) {
        this.ctx = ctx;
        this.chartType = chartType;
        this.init(ctx);

    }

    public init(ctx: WidgetContext) {
        // @ts-ignore
        this.chart = Highcharts.chart(ctx.$container[0].id, {
            chart: {
                type: 'pie',
                animation: {
                    duration: 50
                }
            },
            title: {
                text: '',
                align: 'left'
            },
            credits: {
                enabled: false
            },
            series: this.series(ctx)
        });
    }

    public series(ctx: WidgetContext): any {
        const data = ctx.data;
        const latestData = ctx.latestData;
        const seriesData: Array<{ name: string, y: number }> = [];
        data.forEach((value, index, array) => {
            const groupName = (latestData?.[index]?.data?.[0]?.[1] || '') + '';
            const preIndex = seriesData.findIndex(value1 => value1.name === groupName);
            const newData = value.data.reduce((previousValue, currentValue) => {
                return previousValue + (currentValue?.[1] || 0);
            }, 0);
            if (preIndex > -1) {
                seriesData[preIndex].y += newData;
            }else{
                seriesData.push({
                    name: groupName,
                    y: newData
                });
            }
        });
        return [{
            data: seriesData
        }];
    }

    public update() {
        this.chart.series[0].update(this.series(this.ctx)[0]);
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
