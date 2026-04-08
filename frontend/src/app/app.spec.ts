import { TestBed } from '@angular/core/testing';
import { App } from './app';

// Pruebas basicas de render del componente principal.
describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    // Verifica que el componente se inicializa.
    expect(app).toBeTruthy();
  });

  it('should render title', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    // Verifica titulo principal esperado en UI.
    expect(compiled.querySelector('h1')?.textContent).toContain('BookIt');
  });
});
