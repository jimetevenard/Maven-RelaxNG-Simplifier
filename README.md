# Maven RelaxNG Simplifier Plugin

Based on [Jing](https://github.com/relaxng/jing-trang).

## Usage



    <execution>
        <configuration>
            <schemas>
                <schema>
                    <sourcePath>src/test/my-schema.rng</sourcePath>
                    <targetPath>src/test/simple.srng</targetPath>
                    <!-- paths are relative to project -->
                </schema>
                <schema>
                    <sourcePath>src/main/grammar.rng</sourcePath>
                    <!-- default targetPath is {sourcePath}.simplified.rng -->
                </schema>
            </schemas>
        </configuration>
    </execution>

    