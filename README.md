![Stability Logo](https://raw.githubusercontent.com/danielmills/Stability/master/build/stability.png)

# Stability
Stability to the minecraft servers! Stability uses samples to analyze what is going on, and what can be done to eliminate server lag / other issues. What Stability does NOT do is clear lag on a timer. Stability is automatic, and quick to do what it needs to do when needed. Stability also keeps a short history of the past samples. Therefore, if the ram is low, stability knows that the mobs, and items did not cause it, since we can measure the increase in chunk load & player count. This can help stability clear chunks instead of wasting time, resources, and player patience.

## Monitoring
Stability allows you to tap into it's sampling very esily, and clearly. Simply use /stability monitor (/st mon). You will see a live updating title message, and given a map with live charting for TPS RAM and CHUNKS.

## Actions
Sometimes, there may be some resource fighting, which stability cannot detect. In this case, we have actions. They allow you to request stability clear unused chunks, or remove redstone clocks. Actions are very important, simply because stability is advanced, leaving many analytical pitfalls.

## Configuration
An important part of making stability work for your server, is the configuration. Not every server is the same, nor is it backed with the same processor (or array of them!). This means that "lag" is a different number on every server (decimals). In the configuration, you are able to define a threshold for many "sample components". Please check the wiki on the right for more information on configuring

## Commands & Permissions
Commands and permissions are located on the wiki also!

# Downloads
[RELEASE v1.5.4](https://github.com/danielmills/Stability/blob/master/build/latest/release/Stability.jar?raw=true)

[BLOOD CLOTTING EDGE v1.5.5] (https://github.com/danielmills/Stability/blob/master/build/latest/bleeding/Stability.jar?raw=true)