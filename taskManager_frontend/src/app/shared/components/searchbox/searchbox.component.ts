import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { debounceTime, Subject, Subscription } from 'rxjs';

@Component({
  selector: 'shared-searchbox',
  standalone: false,
  templateUrl: './searchbox.component.html',
  styleUrl: './searchbox.component.scss'
})
export class SearchboxComponent implements OnInit, OnDestroy {
  private readonly debouncer: Subject<string> = new Subject<string>();
  private debouncerSubscription?: Subscription;

  @Output()
  public onValue: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  public onDebounce: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit(): void {
    this.debouncerSubscription = this.debouncer
      .pipe(
        debounceTime(300)
      )
      .subscribe( value => {
        this.onDebounce.emit( value )
      })
  }

  ngOnDestroy(): void {
    this.debouncerSubscription?.unsubscribe();
  }

  emitValue( value: string ): void {
    this.onValue.emit( value );
  }

  onKeyPress(searchTerm: string) {
    this.debouncer.next( searchTerm );
  }
}
