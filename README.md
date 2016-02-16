![Stability Logo](https://raw.githubusercontent.com/danielmills/Stability/master/build/stability.png)

# Stability
Stability to the minecraft servers! Stability uses samples to analyze what is going on, and what can be done to eliminate server lag / other issues. What Stability does NOT do is clear lag on a timer. Stability is automatic, and quick to do what it needs to do when needed. Stability also keeps a short history of the past samples. Therefore, if the ram is low, stability knows that the mobs, and items did not cause it, since we can measure the increase in chunk load & player count. This can help stability clear chunks instead of wasting time, resources, and player patience.

## Monitoring
Stability allows you to tap into it's sampling very esily, and clearly. Simply use /stability monitor (/st mon). You will see a live updating title message, and given a map with live charting for TPS RAM and CHUNKS.

## Actions
Sometimes, there may be some resource fighting, which stability cannot detect. In this case, we have actions. They allow you to request stability clear unused chunks, or remove redstone clocks. Actions are very important, simply because stability is advanced, leaving many analytical pitfalls. You can also stack trace the server.

## Configuration
An important part of making stability work for your server, is the configuration. Not every server is the same, nor is it backed with the same processor (or array of them!). This means that "lag" is a different number on every server (decimals). In the configuration, you are able to define a threshold for many "sample components". Please check the wiki on the right for more information on configuring

## Commands & Permissions
Commands and permissions are located on the wiki also!

# Downloads
[RELEASE v1.7](https://github.com/danielmills/Stability/blob/master/build/latest/release/Stability.jar?raw=true)

## Changelog

### Stability 1.7
Stability 1.7 Revamps the map, increases performance up another notch, and adds MAHS (memory stuff)
* Chunk Bugfixes (judgement when to act upon chunks or not)
* Memory Allocations Per Second in megabytes comparing Seconds per garbage collection
* Overhaul to the dispatcher, and basically more optimizations among adding the new sample
* MAHS now averages based on the ammount of garbage collections. More activity, more accurate; less, smoother.
* Small TPS measurement change. This will make the tps spike back up faster, less averaging.
* Fixed a potential small memory leak in the sampler.
* Performance optimizations for the Stack Trace measurment.
* Action Tasker now defers purging chunks unless needed. (less purging for no reason)
* Better judgement on if the server is lagging or not. (notification of lag will be faster)

### Stability 1.6.1.1
Stability 1.6.1.1 Adds another command, chunklagtp (/st cltp)
* Small bugfixees
* Add command to tp admin to chunk with most entities in it (/st cltp)

### Stability 1.6.1
Stability 1.6.1 Makes use of its GUI api more often, and works on display of dispatching info.
* Using /st history now shows a gui of history instead of a list.
* The Dispatcher changes positions depending on if you are moving or not (try it out)
* Adds several new config options which are not used yet but will be soon!

### Stability 1.6
Stability 1.6 Adds many fixes, including several important ones. The GUI / SubSubTitle Update!
* Uses Sub-Sub titles instead of subtitles for information
* Uses Sub titles for actions instead of titles
* Stack Tracing now sends a report via gui!
* Fixed Dispatch info being formatted for every message, now per dispatch, not per player per dispatch.
* Some Tweaks, but more planned.

### Stability 1.5.5.2
Stability 1.5.5.1 Fixes chunks from being unloaded even further if it isnt effective.
* If the previous chunk unload operation did not unload more than 256 chunks, prevent then next.
* A cooldown will be thrown for about half a minute preventing stability from trying again.
* Prevents chunks from being unladed unless they are above 600, (default world + a bit more)

### Stability 1.5.5
Stability 1.5.5 adds several new and updated features plus some bug fixes!
* Fixed the Status screen (/st status) To display information such as your stability percentage
* Analyses sample data and determines suggestions and issues with your server on the /st status page
* View the history of actions by stability with /st history. Displayed such as CHUNK PURGE: 9 Minutes ago.
* Calculates the servers stability. How stable it is. Its not exactly mathematically calculated.
* Performance optimizations for chunks & dealing with instabilities.
* More to come... It isnt done and is highly unrecomended you actually use it.
