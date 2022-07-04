#Live Score Board

##How To Use:
    > Expected behaviours can be seen through by running the unit tests it self.
    > To Verify the Generate Summary is working fine then you can change the test cases, 
      mainly changing the finished game method call will show the difference.
    > Test can be run by
        > mvn install -- tests both build and tests.

## Assumptions:
    > Update Score will only be performed on Active Games.
    > There can be multiple active games.
    > Summary is only generated on any finished Game. No Active game will be considered.

This Library can also be used in a separate java project by running 'mvn install' then using the snapshot version in the other locally available project.