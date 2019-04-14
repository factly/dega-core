db.createCollection('dega_user');
db.dega_user.createIndex({ email: 1 }, { unique: true });
db.dega_user.createIndex({ slug: 1 }, { unique: true });

db.createCollection('category');
db.category.createIndex({ name: 1 }, { unique: true });

db.createCollection('format');
db.format.createIndex({ name: 1 }, { unique: true });

db.createCollection('organization');
db.organization.createIndex({ name: 1 }, { unique: true });
db.organization.createIndex({ client_id: 1 }, { unique: true });

db.createCollection('role');
db.role.createIndex({ name: 1 }, { unique: true });

db.createCollection('status');
db.status.createIndex({ name: 1 }, { unique: true });

db.createCollection('tag');
db.tag.createIndex({ name: 1 }, { unique: true });
