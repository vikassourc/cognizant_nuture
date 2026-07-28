import { Component } from '@angular/core';
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
}