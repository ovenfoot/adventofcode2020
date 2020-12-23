package com.ovenfoot.adventofcode2020.day23;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    private Integer value;
    public Cup clockWise;
    public Cup counterClockwise;

    public Cup(int val) {
        this.value = val;
        this.clockWise = null;
        this.counterClockwise = null;
    }

    public Integer getValue() {
        return value;
    }

    public Cup(int val, Cup clockwise, Cup counterClockwise) {
        this.value = val;
        this.clockWise = clockwise;
        this.counterClockwise = counterClockwise;
    }

    public Cup getNthClockwise(int n) {
        Cup ret = this;
        for (int i = 0; i < n; i++) {
            ret = ret.clockWise;
        }
        return ret;
    }

    public void insertClockwise(Cup cup) {
        Cup originalClockwise = clockWise;
        originalClockwise.counterClockwise = cup;
        this.clockWise = cup;
        cup.counterClockwise = this;
        cup.clockWise = originalClockwise;
    }

    public void insertClockwise(List<Cup> cups) {
        Cup originalClockwise = clockWise;
        Cup prevCup = this;

        for (Cup cup: cups) {
            cup.counterClockwise = prevCup;
            prevCup.clockWise = cup;
            prevCup = cup;
        }

        prevCup.clockWise = originalClockwise;
        originalClockwise.counterClockwise = prevCup;
    }

    public Cup removeFromClockwise(int nthCup) {
        Cup currentCup = this;
        for (int i = 0; i < nthCup; i++) {
            currentCup = currentCup.clockWise;
        }
        Cup prevCup = currentCup.counterClockwise;
        Cup nextCup = currentCup.clockWise;

        prevCup.clockWise = nextCup;
        nextCup.counterClockwise = prevCup;

        currentCup.counterClockwise = null;
        currentCup.clockWise = null;
        return currentCup;
    }

    public List<Cup> removeFromClockwise(int nthCup, int nCups) {
        List<Cup> returnCups = new ArrayList<>();
        Cup currentCup = this;
        for (int i = 0; i < nthCup; i++) {
            currentCup = currentCup.clockWise;
        }
        Cup prevCup = currentCup.counterClockwise;
        for (int i = 0; i < nCups; i++) {
            Cup cupToAdd = currentCup;
            currentCup = currentCup.clockWise;
            cupToAdd.clockWise = null;
            cupToAdd.counterClockwise = null;
            returnCups.add(cupToAdd);

        }

        Cup nextCup = currentCup;
        prevCup.clockWise = nextCup;
        currentCup.counterClockwise = prevCup;

        return returnCups;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Cup) {
            return ((Cup) other).value == this.value;
        } else if (other instanceof Integer) {
            return (int) other == this.value;
        }
        return false;
    }

    public String toString() {
        return value.toString();
    }
}
