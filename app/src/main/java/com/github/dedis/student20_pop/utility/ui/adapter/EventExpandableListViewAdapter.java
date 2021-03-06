package com.github.dedis.student20_pop.utility.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.github.dedis.student20_pop.R;
import com.github.dedis.student20_pop.model.event.Event;
import com.github.dedis.student20_pop.model.event.EventCategory;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static com.github.dedis.student20_pop.model.event.EventCategory.*;

public abstract class EventExpandableListViewAdapter extends BaseExpandableListAdapter {
  protected final Context context;
  protected final List<EventCategory> categories;
  protected final HashMap<EventCategory, List<Event>> eventsMap;
  protected static final SimpleDateFormat DATE_FORMAT =
      new SimpleDateFormat("dd/MM/yyyy HH:mm z", Locale.ENGLISH);

  /**
   * Constructor for the expandable list view adapter to display the events in the attendee UI
   *
   * @param context
   * @param events the list of events of the lao
   */
  public EventExpandableListViewAdapter(Context context, List<Event> events) {
    this.context = context;
    this.eventsMap = new HashMap<>();
    this.categories = new ArrayList<>();
    this.categories.add(PAST);
    this.categories.add(PRESENT);
    this.categories.add(FUTURE);
    this.eventsMap.put(PAST, new ArrayList<>());
    this.eventsMap.put(PRESENT, new ArrayList<>());
    this.eventsMap.put(FUTURE, new ArrayList<>());

    putEventsInMap(events, this.eventsMap);
    orderEventsInMap(this.eventsMap);
  }

  /** @return the amount of categories */
  @Override
  public int getGroupCount() {
    return this.eventsMap.size();
  }

  /**
   * @param groupPosition
   * @return the amount of events in a given group
   */
  @Override
  public int getChildrenCount(int groupPosition) {
    return this.eventsMap.get(this.categories.get(groupPosition)).size();
  }

  /**
   * @param groupPosition
   * @return the category of a given position
   */
  @Override
  public Object getGroup(int groupPosition) {
    if (groupPosition >= getGroupCount()) {
      return null;
    }
    return this.categories.get(groupPosition);
  }

  /**
   * @param groupPosition
   * @param childPosition
   * @return the event for a given position in a given category
   */
  @Override
  public Object getChild(int groupPosition, int childPosition) {

    if (groupPosition >= getGroupCount()) {
      return null;
    }
    if (childPosition >= getChildrenCount(groupPosition)) {
      return null;
    }

    return this.eventsMap.get(this.categories.get(groupPosition)).get(childPosition);
  }

  /**
   * Gets the ID for the group at the given position. This group ID must be unique across groups.
   * The combined ID (see {@link #getCombinedGroupId(long)}) must be unique across ALL items (groups
   * and all children).
   *
   * @param groupPosition the position of the group for which the ID is wanted
   * @return the ID associated with the group
   */
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  /**
   * Gets the ID for the given child within the given group. This ID must be unique across all
   * children within the group. The combined ID (see {@link #getCombinedChildId(long, long)}) must
   * be unique across ALL items (groups and all children).
   *
   * @param groupPosition the position of the group that contains the child
   * @param childPosition the position of the child within the group for which the ID is wanted
   * @return the ID associated with the child
   */
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  /**
   * Indicates whether the child and group IDs are stable across changes to the underlying data.
   *
   * @return whether or not the same ID always refers to the same object d
   */
  @Override
  public boolean hasStableIds() {
    return false;
  }

  /**
   * Gets a View that displays the given group. This View is only for the group--the Views for the
   * group's children will be fetched using {@link #getChildView(int, int, boolean, View,
   * ViewGroup)}.
   *
   * @param groupPosition the position of the group for which the View is returned
   * @param isExpanded whether the group is expanded or collapsed
   * @param convertView the old view to reuse, if possible. You should check that this view is
   *     non-null and of an appropriate type before using. If it is not possible to convert this
   *     view to display the correct data, this method can create a new view. It is not guaranteed
   *     that the convertView will have been previously created by {@link #getGroupView(int,
   *     boolean, View, ViewGroup)}.
   * @param parent the parent that this view will eventually be attached to
   * @return the View corresponding to the group at the specified position
   */
  @Override
  public abstract View getGroupView(
      int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

  /**
   * Gets a View that displays the data for the given child within the given group.
   *
   * @param groupPosition the position of the group that contains the child
   * @param childPosition the position of the child (for which the View is returned) within the
   *     group
   * @param isLastChild Whether the child is the last child within the group
   * @param convertView the old view to reuse, if possible. You should check that this view is
   *     non-null and of an appropriate type before using. If it is not possible to convert this
   *     view to display the correct data, this method can create a new view. It is not guaranteed
   *     that the convertView will have been previously created by {@link #getChildView(int, int,
   *     boolean, View, ViewGroup)}.
   * @param parent the parent that this view will eventually be attached to
   * @return the View corresponding to the child at the specified position
   */
  @Override
  public View getChildView(
      int groupPosition,
      int childPosition,
      boolean isLastChild,
      View convertView,
      ViewGroup parent) {
    // TODO : For the moment, events are displayed the same if user is attendee or organizer,
    // in the future it could be nice to have a pencil icon to allow organizer to modify an event
    Event event = ((Event) getChild(groupPosition, childPosition));
    String eventTitle = (event.getName() + " : " + event.getType());
    String eventDescription = "";
    String time = DATE_FORMAT.format(event.getStartTime() * 1000L);
    if (convertView == null) {
      LayoutInflater inflater =
          (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.layout_event, null);
    }
    TextView eventTitleTextView = convertView.findViewById(R.id.event_title);
    TextView descriptionTextView = convertView.findViewById(R.id.event_description);
    eventTitleTextView.setText(eventTitle);
    switch (event.getType()) {
      case ROLL_CALL:
        eventDescription =
            "Start Time : "
                + time
                + "\nLocation : "
                + event.getLocation()
                + "\nParticipants: "
                + event.getAttendees().size();
        break;
      default:
        eventDescription = "Start Time : " + time + "\nLocation : " + event.getLocation();
        break;
    }
    descriptionTextView.setText(eventDescription);
    return convertView;
  }

  /**
   * A helper method that places the events in the correct key-value pair according to their times
   *
   * @param events
   * @param eventsMap
   */
  private void putEventsInMap(List<Event> events, HashMap<EventCategory, List<Event>> eventsMap) {
    for (Event event : events) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DATE, -1);
      long yesterday = (Instant.ofEpochMilli(calendar.getTimeInMillis())).getEpochSecond();
      // later: event.getEndTime() < now
      if (event.getStartTime() < yesterday) {
        eventsMap.get(PAST).add(event);
      }
      // later: event.getStartTime()<now && event.getEndTime() > now
      else if (event.getStartTime() <= Instant.now().getEpochSecond()) {
        eventsMap.get(PRESENT).add(event);
      } else { // if e.getStartTime() > now
        eventsMap.get(FUTURE).add(event);
      }
    }
  }

  /**
   * A helper method that orders the events according to their times
   *
   * @param eventsMap
   */
  private void orderEventsInMap(HashMap<EventCategory, List<Event>> eventsMap) {

    for (EventCategory category : categories) {
      Collections.sort(eventsMap.get(category), new EventComparator());
    }
    // 2 possibilities: B strictly after A or B nested within A
  }

  /**
   * Whether the child at the specified position is selectable.
   *
   * @param groupPosition the position of the group that contains the child
   * @param childPosition the position of the child within the group
   * @return whether the child is selectable.
   */
  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  private static class EventComparator implements Comparator<Event> {
    // later: compare start times
    @Override
    public int compare(Event event1, Event event2) {
      return Long.compare(event1.getTime(), event2.getTime());
    }
  }
}
