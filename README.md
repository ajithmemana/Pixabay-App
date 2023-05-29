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



# Comments

Pros:
    Good looking UI
    Usage of LazyStaggeredGrid

Resolved:
    Usage of Gson with Kotlin not ideal
    Doesn't use Retrofit's built-in coroutine support
    Internet Connectivity detector is buggy
    Uses global variables

To be improved:
    No tests
    Understanding of Dependency Injection 

TODO:
Dynamic colors - https://m3.material.io/styles/color/dynamic-color/overview
Preview diff screens - https://developer.android.com/jetpack/compose/tooling/previews
