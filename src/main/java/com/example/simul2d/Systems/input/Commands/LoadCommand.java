package com.example.simul2d.Systems.input.Commands;

import java.io.File;
import java.nio.file.Path;

public record LoadCommand(String filePath) implements Command{
}
