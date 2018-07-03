# Maven RelaxNG Simplifier Plugin

Based on [Jing](https://github.com/relaxng/jing-trang).

This plugin takes one or more specified RelaxNG Schemas and outputs a simplified schema
according to the [Simplified RelaxNG Specification](http://relaxng.org/spec-20011203.html#simplification)

*This plugin does nothing by itself.*  
It just calls [Jing](https://github.com/relaxng/jing-trang) to do the job.

## Usage

You can find a sample utilisation project here :
<http://TODO/>


    <execution>
		<goals>...</goals>
		
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
	

    