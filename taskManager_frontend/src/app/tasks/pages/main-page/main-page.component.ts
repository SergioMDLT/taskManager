import { Component, HostListener, OnInit } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'tasks-main-page',
  standalone: false,
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit {

  public tasks: Task[] = [];
  public isLoading: boolean = false;
  public searchTerm: string = '';
  public totalPages: number = 0;
  public sortField: string = 'priority';

  private readonly searchKey = 'main-search-term';
  private readonly tasksKey = 'main-tasks';
  private readonly pageSize: number = 15;
  private currentPage: number = 0;

  constructor (
    private readonly taskService: TasksService,
    private readonly toastService: ToastService
   ) { }

  ngOnInit(): void {
    this.loadStateFromStorage();
  }

  private isLocalStorageAvailable(): boolean {
    return typeof localStorage !== 'undefined';
  }

  private loadStateFromStorage(): void {
    if ( !this.isLocalStorageAvailable() ) {
      this.loadTasks();
      return;
    }

    const storedSearch = localStorage.getItem( this.searchKey ) ;
    const storedTasks = localStorage.getItem( this.tasksKey );

    if ( storedSearch ) {
      this.searchTerm = storedSearch;
      if ( storedTasks ) {
        this.tasks = JSON.parse( storedTasks );
        return;
      }
      this.onSearch( storedSearch );
    } else {
      this.loadTasks();
    }
  }

  loadTasks(): void {
    if ( this.currentPage >= this.totalPages && this.totalPages !== 0 ) return;
    this.isLoading = true;

    this.taskService.getTasks({
      completed: false,
      page: this.currentPage,
      size: this.pageSize,
      sort: this.sortField
    }).subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = [ ...this.tasks, ...tasks.content ];
          this.currentPage++;
          this.totalPages = tasks.totalPages;
          this.saveTasksToStorage();
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error( 'Error loading tasks:', err );
        this.toastService.showError( 'Error loading tasks' );
        this.isLoading = false;
      },
    });
  }

  onSearch( term: string ): void {
    this.searchTerm = term;
    this.currentPage = 0;
    this.tasks = [];

    if ( term.trim().length === 0 ) {
      localStorage.removeItem( this.searchKey );
      localStorage.removeItem( this.tasksKey );
      this.loadTasks();
      return;
    }

    localStorage.setItem( this.searchKey, term );
    this.isLoading = true;

    this.taskService.getTasks({
      completed: false,
      title: term,
      page: this.currentPage,
      size: this.pageSize,
      sort: this.sortField
    }).subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks.content;
          this.currentPage++;
          this.totalPages = tasks.totalPages;
          this.saveTasksToStorage();
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error( 'Error searching tasks:', err );
        this.toastService.showError( 'Error searching tasks' );
        this.isLoading = false;
      },
    });
  }

  private saveTasksToStorage(): void {
    if ( this.isLocalStorageAvailable() ) {
      localStorage.setItem( this.tasksKey, JSON.stringify( this.tasks ) );
    }
  }

  @HostListener( 'window:scroll', [])
  onScroll(): void {
    const threshold = 50;
    const position = window.innerHeight + window.scrollY;
    const height = document.body.offsetHeight;

    if ( position >= height - threshold && !this.isLoading ) this.loadTasks();
  }

}
