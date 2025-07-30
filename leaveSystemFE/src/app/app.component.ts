import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'leaveSystemFE';

  tab = 'dashboard';

  isDarkMode = false;

  constructor(private renderer: Renderer2) {}

  ngOnInit() {
    this.renderer.setAttribute(document.documentElement, 'data-bs-theme', 'light');
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    const theme = this.isDarkMode ? 'dark' : 'light';
    this.renderer.setAttribute(document.documentElement, 'data-bs-theme', theme);
  }
}
