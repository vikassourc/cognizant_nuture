const fs = require('fs');
const path = require('path');

const dirs = [
  'src/app/components/course-card'
];

dirs.forEach(d => fs.mkdirSync(d, { recursive: true }));

const files = {
  // Home Component
  'src/app/pages/home/home.component.ts': `import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  portalName = 'Student Course Portal';
  isPortalActive = true;
  message = '';
  searchTerm = '';

  // [property] is a one-way binding from component to DOM.
  // [(ngModel)] is a two-way binding that syncs DOM input changes to the component and vice-versa.

  ngOnInit() {
    console.log('HomeComponent initialised — courses loaded');
  }

  ngOnDestroy() {
    console.log('HomeComponent destroyed');
  }

  onEnrollClick() {
    this.message = 'Enrollment opened!';
  }
}`,

  'src/app/pages/home/home.component.html': `<div class="home-container">
  <h1>{{ portalName }}</h1>
  <p>Manage your enrollments, view your grades, and discover new courses.</p>
  
  <div class="stats-row">
    <div class="stat">Courses Available: 12</div>
    <div class="stat">Enrolled: 3</div>
    <div class="stat">GPA: 3.8</div>
  </div>

  <div class="actions">
    <button [disabled]="!isPortalActive" (click)="onEnrollClick()">Enroll Now</button>
    <p>{{ message }}</p>

    <input type="text" [(ngModel)]="searchTerm" placeholder="Search..." />
    <p>Searching for: {{ searchTerm }}</p>
  </div>
</div>`,

  // Course Card Component
  'src/app/components/course-card/course-card.component.ts': `import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-course-card',
  standalone: true,
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css']
})
export class CourseCardComponent implements OnChanges {
  @Input() course!: { id: number, name: string, code: string, credits: number };
  @Output() enrollRequested = new EventEmitter<number>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['course']) {
      console.log('CourseCardComponent course changed:', changes['course'].previousValue, '->', changes['course'].currentValue);
    }
  }
}`,

  'src/app/components/course-card/course-card.component.html': `<div class="course-card">
  <h3>{{ course.name }} ({{ course.code }})</h3>
  <p>Credits: {{ course.credits }}</p>
  <button (click)="enrollRequested.emit(course.id)">Enroll</button>
</div>`,

  'src/app/components/course-card/course-card.component.css': `.course-card { border: 1px solid #ccc; padding: 10px; margin: 10px; border-radius: 4px; }`,

  // Course List Component
  'src/app/pages/course-list/course-list.component.ts': `import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseCardComponent } from '../../components/course-card/course-card.component';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, CourseCardComponent],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent {
  courses = [
    { id: 1, name: 'Angular Basics', code: 'CS101', credits: 3 },
    { id: 2, name: 'React Fundamentals', code: 'CS102', credits: 3 },
    { id: 3, name: 'Vue for Beginners', code: 'CS103', credits: 2 },
    { id: 4, name: 'Advanced Svelte', code: 'CS104', credits: 4 },
    { id: 5, name: 'Web Components', code: 'CS105', credits: 1 }
  ];

  selectedCourseId: number | null = null;

  onEnroll(courseId: number) {
    console.log('Enrolling in course: ' + courseId);
    this.selectedCourseId = courseId;
  }
}`,

  'src/app/pages/course-list/course-list.component.html': `<h2>Course List</h2>
<app-course-card 
  *ngFor="let c of courses" 
  [course]="c" 
  (enrollRequested)="onEnroll($event)">
</app-course-card>
<p *ngIf="selectedCourseId">Selected course ID: {{ selectedCourseId }}</p>`,
};

Object.entries(files).forEach(([file, content]) => fs.writeFileSync(file, content));
