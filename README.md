# radar-ppi
Radar PPI

## Background

This is a JavaFX application to emulate an 'olde' radar PPI display:

- the display scope has a hue
- the sweep fades out as it rotates
- the plots fade out over over one complete sweep

## Controls

There are basic keyboard controls:

- 'a' will cycle through annotation style on the range and bearing reticle
- '1'..'5' will select a maximum range for the reticle

## Modes

It operates in one of two modes:

- simulation of tracks or
- request of tracks from the ADS-B data source provided by the companion project dump1090-processor available in my GitHub repo

## Running

In order to run this program (independently from Maven) the JavaFX runtime must be installed on the local PC,
it can be downloaded from [JavaFX](https://gluonhq.com/products/javafx/)

To launch the JavaFX must be on the JVM modulepath and the following command line options are necessary

    --add-modules=javafx.controls -Dprism.maxTextureSize=8192 -Dprism.targetvram=2G
