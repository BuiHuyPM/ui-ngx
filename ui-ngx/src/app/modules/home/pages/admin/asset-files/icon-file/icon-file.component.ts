import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'tb-icon-file',
  templateUrl: './icon-file.component.html',
  styleUrls: ['./icon-file.component.scss']
})
export class IconFileComponent implements OnInit {
  @Input() fileName: string;
  @Input() isFolder: boolean;

  constructor() {

  }

  ngOnInit(): void {
  }

  isImage(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && (fileName.endsWith('.png') || fileName.endsWith('.jpg') || fileName.endsWith('.svg') || fileName.endsWith('.webp'));
  }

  isDoc(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && (fileName.endsWith('.json') || fileName.endsWith('.doc') || fileName.endsWith('.docx') || fileName.endsWith('.xlsx') || fileName.endsWith('.xls'));
  }
  isPdf(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && fileName.endsWith('.pdf');
  }
  isHtml(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && (fileName.endsWith('.html') || fileName.endsWith('.md'));
  }
  isJs(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && fileName.endsWith('.js');
  }
  isCss(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && fileName.endsWith('.css');
  }
  isGif(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && fileName.endsWith('.gif');
  }
  isFont(): boolean {
    const fileName = this.fileName.toLowerCase();
    return !this.isFolder && ( fileName.endsWith('.ttf') || fileName.endsWith('.woff') || fileName.endsWith('.otf') || fileName.endsWith('.woff2') );
  }
}
