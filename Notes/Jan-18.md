# January 18th

* meetings in the week.

#QA

1. No winner or lose condition, optional feature.
2. Tick is just for debug.

BONUS 2 marks - aditional features

## Use cases
Play mode:

### Start
1. Press Start button.
2. Run game loop.

precondition:
- app is open and running
- have to be in game mode
- game have to be stopped
- it can run with empty black screen 

postconditions:
- loop runs
- hidden grid of build mode

### Pause
1. Press Pause button.
2. pause the game loop.

precondition:
- app is open and running
- have to be in game mode
- game have to be playing

postconditions:
- pause the game, but not going to build mode
- loop stops
- unlocks build mode

### Exit
1. Click quit.
2. Ask for saving the built.
3. Exits the game.

precondition:
- app is open and running
- have to be in game or build mode.
- built has not been saved.

postconditions:
- build is saved or discarded
- application is closed

### Switch mode
1. Switch mode Button is pressed.
2. toggle to alternative mode.

precondition:
- app is open and running

postconditions:
- displays alternative view

### Manual Trigger flippers
1. User presses mapped button.
2. Flipper rotates.
3. User releases the button.
4. Flipper returns to the original state.

precondition:
- should be a mapping to button.
- should be in run mode.

postconditions:
- flipper is new position 


### Ball hits gizmo
1. Ball is in motion. 
2. Ball collides with gizmo.
3. Ball changes direction and velocity based on reflection coeficient.
4. Continues to have motion.

precondition:
- should be in run mode.
- game should be not paused.
- balls been shot from absorber.

postconditions:
- ball changes it's direction and/or speed.

### Ball hits absorber

1. Ball is in motion. 
2. Ball collides with the absorber.
3. Ball stoped and put in the lower right corner.

precondition:
- should be in run mode.
- game should be not paused.
- balls been shot from absorber.

postconditions:
- ball is stopped and placed in right hand corner of the absorber

### Ball is shot from absorber
1. Absorber is triggered.
2. Ball travels straight up at 50L/s till it hits something.

precondition:
- should be in run mode.
- game should be not paused.
- ball has to be on absorber.

postconditions:
- ball poped out.

### Add Gizmo
1. Pick gizmo.
2. Drag gizmo to board.
3. Release to place.
4. Gizmo added.

precondition:
- should be in build mode.
- board is not full.

postconditions:
- new gizmo is added to board.

