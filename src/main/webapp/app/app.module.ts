import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { TestMemoryH2CoreModule } from 'app/core/core.module';
import { TestMemoryH2AppRoutingModule } from './app-routing.module';
import { TestMemoryH2HomeModule } from './home/home.module';
import { TestMemoryH2EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestMemoryH2SharedModule,
    TestMemoryH2CoreModule,
    TestMemoryH2HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestMemoryH2EntityModule,
    TestMemoryH2AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestMemoryH2AppModule {}
