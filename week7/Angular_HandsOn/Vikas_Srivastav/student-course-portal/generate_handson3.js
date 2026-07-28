const fs = require('fs');
const path = require('path');

const dirs = [
  'src/app/directives',
  'src/app/pipes'
];
dirs.forEach(d => fs.mkdirSync(d, { recursive: true }));

const files = {
  // Custom Directive
  'src/app/directives/highlight.directive.ts': `import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  @Input() appHighlight = 'yellow';

  constructor(private el: ElementRef) {}

  @HostListener('mouseenter') onMouseEnter() {
    this.el.nativeElement.style.backgroundColor = this.appHighlight;
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.el.nativeElement.style.backgroundColor = '';
  }
}`,

  // Custom Pipe
  'src/app/pipes/credit-label.pipe.ts': `import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'creditLabel',
  standalone: true
})
export class CreditLabelPipe implements PipeTransform {
  transform(value: number | null | undefined): string {
    if (!value || value === 0) return 'No Credits';
    if (value === 1) return '1 Credit';
    return \`\${value} Credits\`;
  }
}`,

  // Course Card Component
  'src/app/components/course-card/course-card.component.ts': `import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HighlightDirective } from '../../directives/highlight.directive';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';

@Component({
  selector: 'app-course-card',
  standalone: true,
  imports: [CommonModule, HighlightDirective, CreditLabelPipe],
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css']
})
export class CourseCardComponent implements OnChanges {
  @Input() course!: { id: number, name: string, code: string, credits: number, gradeStatus: string };
  @Input() isEnrolled = false;
  @Output() enrollRequested = new EventEmitter<number>();

  isExpanded = false;

  ngOnChanges(changes: SimpleChanges) {
    if (changes['course']) {
      console.log('CourseCardComponent course changed:', changes['course'].previousValue, '->', changes['course'].currentValue);
    }
  }

  // Getters keep templates clean by avoiding complex logic inside HTML
  get cardClasses() {
    return {
      'card--enrolled': this.isEnrolled,
      'card--full': this.course.credits >= 4,
      'expanded': this.isExpanded
    };
  }
}`,

  'src/app/components/course-card/course-card.component.html': `<div class="course-card" [ngClass]="cardClasses" [ngStyle]="{'border-left-color': course.gradeStatus === 'passed' ? 'green' : (course.gradeStatus === 'failed' ? 'red' : 'grey')}" appHighlight="lightblue">
  <h3>{{ course.name }} ({{ course.code }})</h3>
  <p>{{ course.credits | creditLabel }}</p>
  
  <ng-container [ngSwitch]="course.gradeStatus">
    <span *ngSwitchCase="'passed'" class="badge badge-success">Passed</span>
    <span *ngSwitchCase="'failed'" class="badge badge-danger">Failed</span>
    <span *ngSwitchDefault class="badge badge-secondary">Pending</span>
  </ng-container>

  <button (click)="enrollRequested.emit(course.id)">Enroll</button>
  <button (click)="isExpanded = !isExpanded">Show Details</button>
  
  <div *ngIf="isExpanded" class="details">
    <p>Additional details for {{ course.name }}...</p>
  </div>
</div>`,

  'src/app/components/course-card/course-card.component.css': `.course-card { 
  border: 1px solid #ccc; 
  border-left-width: 5px;
  padding: 10px; 
  margin: 10px; 
  border-radius: 4px; 
  transition: all 0.3s ease;
}
.card--enrolled { background-color: #f0f8ff; }
.card--full { font-weight: bold; }
.expanded { min-height: 150px; }
.badge { padding: 3px 6px; border-radius: 4px; color: white; font-size: 0.8rem; margin-bottom: 10px; display: inline-block; }
.badge-success { background-color: green; }
.badge-danger { background-color: red; }
.badge-secondary { background-color: grey; }`,

  // Course List Component
  'src/app/pages/course-list/course-list.component.ts': `import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseCardComponent } from '../../components/course-card/course-card.component';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, CourseCardComponent],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {
  isLoading = true;
  courses = [
    { id: 1, name: 'Angular Basics', code: 'CS101', credits: 3, gradeStatus: 'passed' },
    { id: 2, name: 'React Fundamentals', code: 'CS102', credits: 3, gradeStatus: 'failed' },
    { id: 3, name: 'Vue for Beginners', code: 'CS103', credits: 2, gradeStatus: 'pending' },
    { id: 4, name: 'Advanced Svelte', code: 'CS104', credits: 4, gradeStatus: 'passed' },
    { id: 5, name: 'Web Components', code: 'CS105', credits: 1, gradeStatus: 'pending' }
  ];

  selectedCourseId: number | null = null;

  ngOnInit() {
    setTimeout(() => {
      this.isLoading = false;
    }, 1500);
  }

  onEnroll(courseId: number) {
    console.log('Enrolling in course: ' + courseId);
    this.selectedCourseId = courseId;
  }

  // trackBy improves performance by only re-rendering elements that changed
  trackByCourseId(index: number, course: any): number {
    return course.id;
  }
}`,

  'src/app/pages/course-list/course-list.component.html': `<h2>Course List</h2>

<p *ngIf="isLoading; else courseGrid">Loading courses...</p>

<ng-template #courseGrid>
  <div *ngIf="courses.length > 0; else noCourses">
    <app-course-card 
      *ngFor="let c of courses; trackBy: trackByCourseId" 
      [course]="c" 
      [isEnrolled]="selectedCourseId === c.id"
      (enrollRequested)="onEnroll($event)">
    </app-course-card>
  </div>
</ng-template>

<ng-template #noCourses>
  <p>No courses available.</p>
</ng-template>

<p *ngIf="selectedCourseId">Selected course ID: {{ selectedCourseId }}</p>`,
};

Object.entries(files).forEach(([file, content]) => fs.writeFileSync(file, content));
