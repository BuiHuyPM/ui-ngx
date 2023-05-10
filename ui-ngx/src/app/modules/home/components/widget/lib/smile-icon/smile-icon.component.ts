import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'tb-smile-icon',
  templateUrl: './smile-icon.component.html',
  styleUrls: ['./smile-icon.component.scss']
})
export class SmileIconComponent implements OnInit {
  @Input() sad: boolean;

  constructor() { }

  ngOnInit(): void {
  }
}
