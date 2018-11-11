

// load org data
print('Loading organizations data.');
loadSeedData(db.organizations, JSON.parse(cat('collections/test/organizations.json')));

// load categories data
print('Loading organizations data.');
loadSeedData(db.categories, JSON.parse(cat('collections/test/categories.json')));

// .. so on

function loadSeedData(collection, json) {
    json.forEach(doc => collection.insert(doc));
}
