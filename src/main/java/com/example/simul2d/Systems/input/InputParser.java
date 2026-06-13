package com.example.simul2d.Systems.input;

import com.example.simul2d.Entities.Entities;
import com.example.simul2d.Systems.input.Commands.*;
import com.example.simul2d.grid.Material;
import com.example.simul2d.grid.Vec2;

public class InputParser {

    private InputParser() {
    }


    /**
     * Parse "(x,y)" → Vec2, or null on failure.
     */
    public static Vec2 parseVec2(String text) {
        if (text == null) return null;
        text = text.trim();

        if (text.length() < 5
                || text.charAt(0) != '('
                || text.charAt(text.length() - 1) != ')') return null;

        int comma = text.indexOf(',');
        if (comma == -1) return null;

        try {
            int x = Integer.parseInt(text.substring(1, comma).trim());
            int y = Integer.parseInt(text.substring(comma + 1, text.length() - 1).trim());
            return new Vec2(x, y);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Reads the next "(…)" token from {@code args} starting at {@code i[0]},
     * advances {@code i[0]} past it, and returns the raw "(…)" string.
     * Returns null if no opening parenthesis is found.
     */
    private static String readVec2Token(String args, int[] i) {
        // skip to the opening '('
        while (i[0] < args.length() && args.charAt(i[0]) != '(') i[0]++;
        if (i[0] >= args.length()) return null;

        int start = i[0];
        int depth = 0;

        while (i[0] < args.length()) {
            char c = args.charAt(i[0]++);
            if (c == '(') depth++;
            else if (c == ')' && --depth == 0) break;
        }

        return args.substring(start, i[0]).trim();
    }


    private static void skipComma(String args, int[] i) {
        if (i[0] < args.length() && args.charAt(i[0]) == ',') i[0]++;
    }


    private static Command parseCommand(String input) {
        int parenOpen = input.indexOf('(');
        if (parenOpen == -1 || !input.endsWith(")")) return null;

        String name = input.substring(0, parenOpen).trim().toLowerCase();
        String args = input.substring(parenOpen + 1, input.length() - 1);
        int[] i = {0};

        return switch (name) {

            case "speed" -> {
                try {
                    yield new SpeedCommand(Integer.parseInt(args.trim()));
                } catch (NumberFormatException e) {
                    yield null;
                }
            }

            case "setmaterial" -> {
                Vec2 pos = parseVec2(readVec2Token(args, i));
                if (pos == null) yield null;
                skipComma(args, i);
                Material mat = Material.fromString(args.substring(i[0]).trim());
                yield mat != null ? new SetMaterialCommand(pos, mat) : null;
            }

            case "addentity" -> {
                Vec2 pos = parseVec2(readVec2Token(args, i));
                if (pos == null) yield null;
                skipComma(args, i);
                Entities entity = Entities.fromString(args.substring(i[0]).trim());
                yield entity != null ? new AddEntityCommand(pos, entity) : null;
            }

            case "removeentity" -> {
                Vec2 pos = parseVec2(readVec2Token(args, i));
                if (pos == null) yield null;
                skipComma(args, i);
                Entities entity = Entities.fromString(args.substring(i[0]).trim());
                yield entity != null ? new RemoveEntityCommand(pos, entity) : null;
            }

            case "clearentities" -> {
                Vec2 pos = parseVec2(args.trim());
                yield pos != null ? new ClearEntitiesCommand(pos) : null;
            }

            case "rectanglematerial" -> {
                Vec2 p1 = parseVec2(readVec2Token(args, i));
                if (p1 == null) yield null;
                skipComma(args, i);
                Vec2 p2 = parseVec2(readVec2Token(args, i));
                if (p2 == null) yield null;
                skipComma(args, i);
                Material mat = Material.fromString(args.substring(i[0]).trim());
                yield mat != null ? new RectangleMaterialCommand(p1, p2, mat) : null;
            }

            case "rectangleentity" -> {
                Vec2 p1 = parseVec2(readVec2Token(args, i));
                if (p1 == null) yield null;
                skipComma(args, i);
                Vec2 p2 = parseVec2(readVec2Token(args, i));
                if (p2 == null) yield null;
                skipComma(args, i);
                Entities entity = Entities.fromString(args.substring(i[0]).trim());
                yield entity != null ? new RectangleEntityCommand(p1, p2, entity) : null;
            }

            default -> null;
        };
    }

    
    public static Command parseInput(String input) {
        if (input == null) return null;
        input = input.trim();
        if (input.isEmpty()) return null;

        return switch (input.toLowerCase()) {
            case "pause", "p" -> new PauseCommand();
            case "1", "2", "3", "4", "5", "6", "7", "8", "9" -> new SpeedCommand(Integer.parseInt(input));
            case "a", "+" -> new IncreaseSpeedCommand();
            case "d", "-" -> new DecreaseSpeedCommand();
            case "save" -> new SaveCommand("Default_save.simul2d");
            case "load" -> new LoadCommand("Default_save.simul2d");
            default -> parseCommand(input);
        };
    }
}