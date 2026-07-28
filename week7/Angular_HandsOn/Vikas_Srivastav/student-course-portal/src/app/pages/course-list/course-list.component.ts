import { Component, OnInit } from '@angular/core';
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
}