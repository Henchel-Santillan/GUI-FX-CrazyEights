package gui.studio.plotter;

/* One way to go about this:
 * when segmentplotter is clicked --> create a segment of default size (segment defs)
 * bind it to mouse 
 * on click, release onto Canvas parent
 * then, allow resize with bounding box (e.g. make resizable using DragResizer)
 * while selected, highlight bounding box + create two visible pivot points at ends of segments
 * allow drag on those segments --> i.e. when they are pulled, extend line in driection of mouse
 * dynamically update start and end x,y of the lines
 * 
 * */
public class SegmentPlotter {
	
}
