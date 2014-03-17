Shapemaker
==========

A graphical program that allows the user to draw vector graphic objects.

The below can be seen in the Help window if you press the Help button in the program window.
============================================================================================



Welcome to Shapemaker by Baranski


H - opens the help window

D - activates the draw tool. The draw tool lets you place individual points one at a time simply by clicking. It can also place many points if you drag the tool across the screen.

S - activates the select tool. The select tool, if clicked at one spot, will select the nearest point to where the tool was clicked. If you press down, drag, and release, you will instead select any points within the selection box.

M - activates the move tool. The move tool lets you move selected points around the screen. You can also do this using the arrow keys.

G - turns off all tools, allowing you to click on and select separate polygons on the screen.

N - creates a new polygon for you to work on.

(+/-) - changes the order of the polygon currently being worked on, allowing you to rearrange the "stacking" of the polygons on the screen.

Delete - deletes selected points. If the polygon you were just working on has no points (either because you never added any or because you deleted them), and you grab another polygon or create another polygon, the polygon you were just working on (the one with no points) will be deleted.

Escape - clears your selection.

C - copies the selected points into a new polygon.

P - prints out the code you would use to generate the selected polygon. You can type in the name of the polygon in the Polygon Output window. Anything you want to do (change the size, orientation, position, or angle of the polygon) to the x and y values of the points for the polygon you can configure in the Polygon Output window as well. When typing in the coordinate formulae, use "#x" and "#y" for the x and y values.

R - Shapemaker automatically shifts the position of your polygon as close to (0, 0) as it can. However, it can do this so that each polygon is considered on its own (relative off) or in the context of the other polygons (relative on). This is important in images with more than one polygon. If relative is ON, the Polygon Output will take into consideration that the polygons are part of one image, and will preserve the relationships between the polygons.

W - Sometimes your polygon can have TO MUCH detail. When that happens, Shapemaker allows you to simplify your polygon by deleting points a minimum distance from each other, a value you can set in the "Control Input" screen at the "Simplification Constant" box. Once you've set the value, just press the "W" key.
