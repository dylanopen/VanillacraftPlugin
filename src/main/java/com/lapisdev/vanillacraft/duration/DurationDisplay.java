package com.lapisdev.vanillacraft.duration;

public class DurationDisplay {
    public static String formatDuration(long duration) {
        long seconds = duration / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long years = days / 365;

        if (seconds < 0) {
            return "Expired";
        }
        if (years > 0) {
            return years + "y " + (days % 365) + "d";
        } else
        if (days > 0) {
            return days + "d " + (hours % 24) + "h";
        } else if (hours > 0) {
            return hours + "h " + (minutes % 60) + "m";
        } else if (minutes > 0) {
            return minutes + "m " + (seconds % 60) + "s";
        } else {
            return seconds + "s";
        }
    }

    public static long fromString(String duration) {
        if (duration.equalsIgnoreCase("permanent")) {
            return Long.MAX_VALUE;
        }
        long totalSeconds = 0;
        String[] parts = duration.split(" ");
        for (String part : parts) {
            long partMagnitude = Long.parseLong(part.substring(0, part.length() - 1));
            if (part.endsWith("y")) {
                totalSeconds += partMagnitude * 31536000;
            } else if (part.endsWith("d")) {
                totalSeconds += partMagnitude * 86400;
            } else if (part.endsWith("h")) {
                totalSeconds += partMagnitude * 3600;
            } else if (part.endsWith("m")) {
                totalSeconds += partMagnitude * 60;
            } else if (part.endsWith("s")) {
                totalSeconds += partMagnitude;
            }
        }
        return totalSeconds * 1000L; // to millis
    }
}
