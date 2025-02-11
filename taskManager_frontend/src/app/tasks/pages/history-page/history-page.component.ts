import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task';
import { TasksService } from '../../services/tasks.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'tasks-history-page',
  standalone: false,
  templateUrl: './history-page.component.html',
  styleUrl: './history-page.component.scss'
})
export class HistoryPageComponent implements OnInit {

  public tasks: Task[] = [];
  public isLoading: boolean = false;
  public searchTerm: string = '';

  private readonly searchKey = 'history-search-term';
  private readonly tasksKey = 'history-tasks';

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

    const storedSearch = localStorage.getItem( this.searchKey );
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
    this.isLoading = true;
    this.taskService.getTasks().subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks;
          this.saveTasksToStorage();
          this.isLoading = false;
        }, 500);
      },
      error: ( err ) => {
        console.error('Error loading tasks:', err);
        this.toastService.showError('Error loading tasks');
        this.isLoading = false;
      },
    });
  }


  onSearch( term: string ): void {
    this.searchTerm = term;

    if ( term.trim().length === 0 ){
      localStorage.removeItem( this.searchKey );
      localStorage.removeItem( this.tasksKey );
      this.loadTasks();
      return;
    }

    localStorage.setItem( this.searchKey, term );
    this.isLoading = true;

    this.taskService.getTasks({ title: term }).subscribe({
      next: ( tasks ) => {
        setTimeout(() => {
          this.tasks = tasks;
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

}
