

// load org data
print('Loading organizations data.');
loadSeedData(db.organization, JSON.parse(cat('collections/test/organization.json')));

// load categories data
print('Loading category data.');
loadSeedData(db.category, JSON.parse(cat('collections/test/category.json')));

// load format data
print('Loading format data.');
loadSeedData(db.format, JSON.parse(cat('collections/test/format.json')));

// load roles data
print('Loading role data.');
loadSeedData(db.role, JSON.parse(cat('collections/test/role.json')));

// load status data
print('Loading status data.');
loadSeedData(db.status, JSON.parse(cat('collections/test/status.json')));

// load tag data
print('Loading tag data.');
loadSeedData(db.tag, JSON.parse(cat('collections/test/tag.json')));


// .. so on

function loadSeedData(collection, json) {
    json.forEach(doc => collection.insert(doc));
}
