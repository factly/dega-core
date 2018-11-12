

// load org data
print('Loading organizations data.');
loadSeedData(db.organizations, JSON.parse(cat('collections/test/organization.json')));

// load categories data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/category.json')));

// load format data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/format.json')));

// load roles data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/role.json')));

// load status data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/status.json')));

// load tag data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/tag.json')));


// .. so on

function loadSeedData(collection, json) {
    json.forEach(doc => collection.insert(doc));
}
