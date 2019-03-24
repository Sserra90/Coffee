# Coffee (In-development)
Easily write readable and structure Instrumented Android tests without **boilerplate and code duplication** using **Kotlin DSL and infix notation**.
Comes with a lot of built-in assertions and view actions to test most common scenarios.

Writing instrumented tests can be a pain and frustrating task in Android world which makes a lot of developers avoid it. 
**Coffee** makes it easier and much faster.

A list of supported views and respective assertions/actions is available below.

# Features

 - Easy API with Kotlin DSL and infix methods
 - Structure instrumented tests with **Pages** and **Views**
 - Avoid AmbiguousMatcher in ViewPager
 - Automatically tries to scroll to target view if necessary
 - A lot of built-in assertions
 - Only one method to work with **shouldBe**
 - Extensible create your custom views if needed
 - Works with RecyclerViews, ScrollViews and ListViews, select elements by position or matcher
 - Works with Horizontal and NestedScrollViews
 
# How to use it

This library is based on **Martin' s Fowler Page Object Pattern** 
https://martinfowler.com/bliki/PageObject.html. 

Everything is organised in **Pages** and **CoffeeViews**. There is a single method **shouldBe** that is used to assert properties on views.

## Lets see a working example

Define the page that contains references to the view you want to act on.

    class RelativePage : Page<RelativePage>() {  
	    val relativeView: RelativeLayoutCoffeeView = RelativeLayoutCoffeeView(R.id.layout)  
	    // You can pass a resource ID or a Matcher<View>
	    val title: TextCoffeeView = TextCoffeeView(R.id.title)  
	    val image: ImageCoffeeView = ImageCoffeeView(R.id.image)  
	    val editText: EditTextCoffeeView = EditTextCoffeeView(R.id.editText) 
	}

    @Test  
    fun testRelativeLayout() {  
        val page = RelativePage()  
        page {  

          relativeView {
            isVisible  
            layoutGravity shouldBe Gravity.CENTER  
            gravity shouldBe Gravity.CENTER  
          }

          // Check TextView properties, text size, text style, etc...
          title {  
            isVisible  
            textAllCaps shouldBe true  
            textStyle shouldBe ITALIC  
            drawableStart shouldBe R.drawable.ic_launcher_background  
          }  

          // Check ImageView properties
          image {  
            isVisible  
            scaleType shouldBe CENTER_CROP  
            alpha shouldBe 0.8F  

            // Check if image is below title view
            isBelow(title)  
          }  

          // Write "Some value" in EditText
          editText.write("Some value")    
     }
    }

## Another example with Popmenu
    class LinearPage : Page<LinearPage>() {  
        val linearLayout: LinearLayoutCoffeeView = LinearLayoutCoffeeView(R.id.layout)  
        val title: CoffeeView = CoffeeView(R.id.title)  
        val popupMenu: PopupMenuCoffeeView = PopupMenuCoffeeView()
    }

    @Test  
    fun testLinearPage() {  
        val page = LinearPage()  
        page {  
          linearLayout {  
            isVisible  
            layoutGravity shouldBe Gravity.CENTER  
            gravity shouldBe Gravity.CENTER  
            orientation shouldBe VERTICAL  
          }
            
          title {  
            weight shouldBe 0.5F  
            // Click on view to open popmenu
            click()  
          }  
      
          popupMenu {  
             isVisible  

             // Check item count
             itemsNr shouldBe 3

             // Item ao position 0, click and check if 
             // ScrollActivity is open.
             itemAt(0) {  
                title shouldBe R.string.action_settings  
                click()
                isOpen<ScrollActivity>()  
            }  
         }
      }  
    }

# Recycler views/List views/Scroll views
Coffee works with RecyclerViews and ListViews with single or multiple view types.
You just need to define a factory that will retrieve the view for each type you want.

Let's see how it works:
  
	// Recycler page defines a RecyclerCoffeeView whose children are CityViews instances
    class RecyclerPage : Page<RecyclerPage>() {  
        val recyclerView: RecyclerCoffeeView = RecyclerCoffeeView(R.id.list) { add<CityView> { CityView(it) } }  
    }  
      
    // Define CityView, when working with Recycler and ListView, extend AdapterCoffeeView.
    class CityView(matcher: Matcher<View>) : AdapterCoffeeView<CityView>(matcher) {  
        val title: TextCoffeeView = TextCoffeeView(withId(R.id.name))  
    }
    
    val page = RecyclerPage()  
    page { 
      
      recyclerView {  
	     isVisible  
	     // Check is using correct layout manager
	     usingLinearLayoutManager
	     
	     itemCount shouldBe 50  
      
	      // Interact with view at position 0
	      atPos<CityView>(0) {  
		      isClickable  
		      title {  
			      text shouldBe "Albania"  
		      }  
          } 
       }
    }

# Create your own view

# Supported views and assertions/actions

Here a list of available views and respective properties and actions you can use.
**Every property and action available in CoffeeView is also available on other views.**

 | Android View                 | Coffee View              | Properties                                                                                                                   | Actions                                                                                                                                   | Positional                                  |
|------------------------------|--------------------------|------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **View**                         | CoffeeView               | ***(Available for every coffee view below)*** isVisibile, isGone, childrenNr, backgroundColor(colors,drawables), elevation(android 5+), isClickable, isNotClickable, weight, alpha | ***(Available for every coffee view below)*** click(), longClick()    | ***(Available for every coffee view below)*** isAbove() isBelow() isLeftOf() isRightOf()
| **TextView**                     | TextCoffeeView           | text, textStyle, textSize, gravity, textAllCaps, textColor, drawableStart, drawableEnd, drawableTop, drawableBottom                   |                                                                                                                                           |                                             |                                                                                                              |
| **Button**                       | ButtonCoffeeView         | Same as TextView                                                                                                             |                                                                                                                                           |                                             |
| **EditText**                     | EditTextCoffeeView       | Same as TextView                                                                                                             | write()                                                                                                                                   |                                             |
| **ImageView**                    | ImageCofeeView           | scaleType                                                                                                                    |                                                                                                                                           |                                             |
| **ImageButton**                  | ImageButtonCoffeeView    | text, textColor, textSize, scaleType                                                                                         |                                                                                                                                           |                                             |
| **LinearLayout**                 | LinearLayoutCoffeeView   | orientation, gravity                                                                                                         |                                                                                                                                           |                                             |
| **RelativeLayout**               | RelativeLayoutCoffeeView | gravity                                                                                                                      |                                                                                                                                           |                                             |
| **ViewGroup**                    | ViewGroupCoffeView       | layoutGravity                                                                                                                |                                                                                                                                           |                                             |
| **Popmenu**                      | PopmenuCoffeeView        | itemsNr, itemTitle                                                                                                           | itemAt(pos), click()                                                                                                                      |                                             |
| **RecyclerView**                 | RecyclerCoffeeView       | itemCount, usingGridLayoutManager, usingLinearLayoutManager                                                                  | atPos(pos), scrollToPos(pos), scrollToTop()                                                                                               |                                             |
| **ScrollView, NestedScrollView** | ScrollCoffeeView         |                                                                                                                              | scrollToTop(), scrollToBottom(), scrollTo(viewId), scrollToViewWithTag(tag), scrollToViewWithText(text), scrollToViewWithMatcher(matcher) |                                             |
| **ViewPager**                    | ViewPagerCoffeeView      |                                                                                                                              | swipeForward(), swipeBackward()                                                                                                           |                                             |
| **Toolbar**                      | ToolbarCoffeeView        | title, subtitle, backButtonIsVisible                                                                                         | clickBackButton                                                                                                                           |                                             |
# Install
Just add gradle dependency


# Contact
sergioserra99@gmail.com
