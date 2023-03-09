import { Observable } from "rxjs";

import { Entity, EntityList } from "src/app/models/entity.model";

export abstract class AbstractDashboardService {

  protected baseUrl = 'http://localhost:8081';

  setUrl(url: string) {
    this.baseUrl += url;
  }

  abstract getAll(): Observable<EntityList<Entity>>;

  abstract deleteById(id: number): void;

  abstract editById(id: number, entity: Entity): void;

  abstract create(entity: Entity): Observable<Entity>;
}
