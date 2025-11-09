export interface Client {
  id: number;
  fullName: string;
  email: string;
  migrated: boolean;
  migratedAt: string | null;
}
