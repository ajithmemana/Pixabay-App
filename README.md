# Developer Notes

 - A simple and basic design is used in the app. 
 - Pagination is not implemented
 - LazyVerticalStaggeredGrid provided by Android is currently in not yet
   production ready. Hence Experimental api annotations are added. This
   has been used instead of standard LazyVerticalGrid for better visual
   appearance ( Images from the api have different aspect ratio, hence
   staggering is better.). Using a grid instead of a simple Column
   allows to present more items in a page.  
 - It is assumed that Pixabay service will provide non null values for
   certain fields and those are marked as non noll in model classes.


