import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatPipe implements PipeTransform {

  transform(value: string): string {
    try {
      return new Date(value).toLocaleDateString();
    }catch (e) {
      return '';
    }
  }

}
