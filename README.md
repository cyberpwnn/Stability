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
[RELEASE v1.5.5.2](https://github.com/danielmills/Stability/blob/master/build/latest/release/Stability.jar?raw=true)

## Changelog

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
