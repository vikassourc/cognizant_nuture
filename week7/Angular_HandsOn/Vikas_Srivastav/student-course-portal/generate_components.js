const fs = require('fs');
const path = require('path');

const dirs = [
  'src/app/components/header',
  'src/app/pages/home',
  'src/app/pages/course-list',
  'src/app/pages/student-profile'
];

dirs.forEach(d => fs.mkdirSync(d, { recursive: true }));

const files = {
  'src/app/components/header/header.component.ts': `import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {}`,

  'src/app/components/header/header.component.html': `<nav>
  <h1>Student Course Portal</h1>
  <ul>
    <li><a routerLink="/">Home</a></li>
    <li><a routerLink="/courses">Courses</a></li>
    <li><a routerLink="/profile">Profile</a></li>
  </ul>
</nav>`,

  'src/app/components/header/header.component.css': ``,

  'src/app/pages/home/home.component.ts': `import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {}`,

  'src/app/pages/home/home.component.html': `<div class="home-container">
  <h1>Welcome to the Student Course Portal</h1>
  <p>Manage your enrollments, view your grades, and discover new courses.</p>
  <div class="stats-row">
    <div class="stat">Courses Available: 12</div>
    <div class="stat">Enrolled: 3</div>
    <div class="stat">GPA: 3.8</div>
  </div>
</div>`,

  'src/app/pages/home/home.component.css': ``,

  'src/app/pages/course-list/course-list.component.ts': `import { Component } from '@angular/core';

@Component({
  selector: 'app-course-list',
  standalone: true,
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent {}`,

  'src/app/pages/course-list/course-list.component.html': `<p>course-list works!</p>`,

  'src/app/pages/course-list/course-list.component.css': ``,

  'src/app/pages/student-profile/student-profile.component.ts': `import { Component } from '@angular/core';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  templateUrl: './student-profile.component.html',
  styleUrls: ['./student-profile.component.css']
})
export class StudentProfileComponent {}`,

  'src/app/pages/student-profile/student-profile.component.html': `<p>student-profile works!</p>`,

  'src/app/pages/student-profile/student-profile.component.css': ``
};

Object.entries(files).forEach(([file, content]) => fs.writeFileSync(file, content));
