function loadSeedData(collection, json) {
    for (var i = 0; i < json.length; i++) {
        collection.insert(json[i]);
    }
}

print('Loading category data.');
loadSeedData(db.category, JSON.parse(cat('collections/data/category.json')));

// load format data
print('Loading format data.');
loadSeedData(db.format, JSON.parse(cat('collections/data/format.json')));

print('Loading organizations data');
loadSeedData(db.organization, JSON.parse(cat('collections/data/organization.json')));

// load roles data
print('Loading role data.');
loadSeedData(db.role, JSON.parse(cat('collections/data/role.json')));

// load status data
print('Loading status data.');
loadSeedData(db.status, JSON.parse(cat('collections/data/status.json')));

// load tag data
print('Loading tag data.');
loadSeedData(db.tag, JSON.parse(cat('collections/data/tag.json')));


// .. so on

