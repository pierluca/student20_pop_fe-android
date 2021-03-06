package com.github.dedis.student20_pop.model.event;

import androidx.databinding.ObservableArrayList;

import java.util.Objects;

import static com.github.dedis.student20_pop.model.event.EventType.ROLL_CALL;
import static com.github.dedis.student20_pop.model.event.RollCallEvent.AddAttendeeResult.ADD_ATTENDEE_ALREADY_EXISTS;
import static com.github.dedis.student20_pop.model.event.RollCallEvent.AddAttendeeResult.ADD_ATTENDEE_SUCCESSFUL;

/** Class modelling a Roll-Call event */
public final class RollCallEvent extends Event {

  private final long endTime;
  private final String description;

  /**
   * Constructor for a Roll-Call Event
   *
   * @param name the name of the roll-call event
   * @param startTime the start time of the roll-call event
   * @param endTime the end time of the roll-call event
   * @param lao the ID of the associated LAO
   * @param attendees the list of attendees of the roll-call
   * @param location the location of the roll-call event
   * @param description the description of the roll-call event
   * @throws IllegalArgumentException if any of the parameters is null
   */
  public RollCallEvent(
      String name,
      long startTime,
      long endTime,
      String lao,
      ObservableArrayList<String> attendees,
      String location,
      String description) {
    super(name, lao, startTime, location, ROLL_CALL);
    if (attendees == null || description == null) {
      throw new IllegalArgumentException("Trying to create a meeting event with null parameters");
    }
    this.endTime = endTime;
    this.description = description;
    this.setAttendees(attendees);
  }

  /** Returns the end time of the Roll-Call. */
  public long getEndTime() {
    return endTime;
  }

  /** Returns the description of the Roll-Call. */
  public String getDescription() {
    return description;
  }

  /**
   * Add an attendee to the Roll-Call's list of attendees
   *
   * @param attendee the ID of the attendee
   * @return AddAttendeeResult
   * @throws IllegalArgumentException if the attendee ID is null
   */
  public AddAttendeeResult addAttendee(String attendee) {
    if (attendee == null) {
      throw new IllegalArgumentException("Trying to add null as an attendee of the Roll-Call");
    }
    if (Objects.requireNonNull(getAttendees()).contains(attendee)) {
      return ADD_ATTENDEE_ALREADY_EXISTS;
    } else {
      getAttendees().add(attendee);
      return ADD_ATTENDEE_SUCCESSFUL;
    }
  }

  /** Type of results when adding an attendee */
  public enum AddAttendeeResult {
    ADD_ATTENDEE_SUCCESSFUL,
    ADD_ATTENDEE_ALREADY_EXISTS,
    ADD_ATTENDEE_UNSUCCESSFUL
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    RollCallEvent that = (RollCallEvent) o;
    return Objects.equals(endTime, that.endTime) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), endTime, description);
  }
}
