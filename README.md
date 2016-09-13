#Have a rest!

String base = "topic/1/article/2/review";
String uri = base+"/3";

Rest rest = new Rest(source, dialect);

###C
Row row = new Row();
row.put("author_id", "12345");
row.put("content", "this is a bad idea!");
row.put("in_time", new Date());
rest.save("topic/1/article/2/review/3", row);

###R
row = rest.find("topic/1/article/2/review/3");

int offset = 0;
int size = 10;
List<Row> rows = rest.list("topic/1/article/2/review", offset, size);

###U
row.put("content", "this is a great idea!");
rest.update("topic/1/article/2/review/3", row);

###D
rest.delete("topic/1/article/2/review/3");